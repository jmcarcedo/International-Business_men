package com.jmcarcedo.internationbusinessmen.core.di

import com.google.gson.GsonBuilder
import com.jmcarcedo.internationbusinessmen.BuildConfig
import com.jmcarcedo.internationbusinessmen.core.data.datasource.HeaderInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val CONNECTION_TIMEOUT = 5

    @JvmStatic
    @Provides
    fun providesHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @JvmStatic
    @Provides
    fun providesLogginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @JvmStatic
    @Provides
    fun providesOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        logginInterceptor: HttpLoggingInterceptor
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), SECONDS)
            .readTimeout(CONNECTION_TIMEOUT.toLong(), SECONDS)
            .addNetworkInterceptor(headerInterceptor)
            .addInterceptor(logginInterceptor)
    }

    @JvmStatic
    @Provides
    fun providesGsonConverterFactory(): Converter.Factory {
        val gson = GsonBuilder().create()
        return GsonConverterFactory.create(gson)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providsRetrofit(
        okHttpClient: OkHttpClient.Builder,
        factoryConverter: Converter.Factory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(factoryConverter)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient.build())
            .build()
    }
}