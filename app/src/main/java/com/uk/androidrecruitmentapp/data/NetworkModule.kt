package com.uk.androidrecruitmentapp.data

import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.uk.androidrecruitmentapp.BuildConfig
import com.uk.androidrecruitmentapp.data.network.ApiService
import com.uk.androidrecruitmentapp.data.network.NetworkDataSourceImpl
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        NetworkModule.Providers::class,
        NetworkModule.Binders::class
    ]
)
abstract class NetworkModule {

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Binders {

        @Binds
        @Singleton
        abstract fun bindNetworkDataSource(networkDataSource: NetworkDataSourceImpl): NetworkDataSource
    }

    @InstallIn(SingletonComponent::class)
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
