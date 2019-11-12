package com.uk.androidrecruitmentapp.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uk.androidrecruitmentapp.data.remote.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(
        includes = [
            AppModule.Binders::class,
            AppModule.Providers::class
        ]
)
abstract class AppModule {
    @Module
    abstract class Binders {

        @Binds
        abstract fun bindContext(application: Application): Context
    }

    @Module
    class Providers {

        @Provides
        @Singleton
        internal fun providesGson(): Gson {
            return GsonBuilder().create()
        }

        @Provides
        @Singleton
        internal fun providesOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }

        @Provides
        @Singleton
        internal fun providesGsonConverterFactory(gson: Gson): GsonConverterFactory {
            return GsonConverterFactory.create(gson)
        }

        @Provides
        @Singleton
        internal fun providesRx2JavaCallAdapterFactory(): RxJava2CallAdapterFactory {
            return RxJava2CallAdapterFactory.create()
        }

        @Provides
        @Singleton
        internal fun providesRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient,
                                      rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit {
            return Retrofit.Builder().addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()
        }

        @Provides
        @Singleton
        internal fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create<ApiService>(ApiService::class.java)
        }
    }

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}