package com.luseen.ribble.di.module

import com.luseen.ribble.BuildConfig
import com.luseen.ribble.data.network.Api
import com.luseen.ribble.data.network.ApiConstants
import com.luseen.ribble.data.network.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by Chatikyan on 29.07.2017.
 */

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging)
        }
        okHttpBuilder.readTimeout(15.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.connectTimeout(15.toLong(), TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstants.ENDPOINT)
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}