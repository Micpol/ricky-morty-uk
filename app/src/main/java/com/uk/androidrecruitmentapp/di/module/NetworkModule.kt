package com.uk.androidrecruitmentapp.di.module

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.uk.androidrecruitmentapp.BuildConfig
import com.uk.androidrecruitmentapp.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = "https://rickandmortyapi.com/api/"
private const val CONNECT_TIMEOUT = 10L
private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 10L

@Module(
    includes = [
        NetworkModule.Providers::class
    ]
)
abstract class NetworkModule {

    @Module
    class Providers {

        @Provides
        @Singleton
        fun provideOkHttpClientBuilder(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(httpLoggingInterceptor)
            }
            return builder.build()
        }

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        @Provides
        @Singleton
        fun provideGsonFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

        @Provides
        @Singleton
        fun provideUnauthorizedApiService(
            httpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): ApiService {
            return Retrofit.Builder()
                .client(httpClient)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(BASE_URL)
                .build()
                .create(ApiService::class.java)
        }
    }
}
