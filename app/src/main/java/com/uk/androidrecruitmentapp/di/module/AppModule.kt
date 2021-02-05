package com.uk.androidrecruitmentapp.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.uk.androidrecruitmentapp.data.source.DataSource
import com.uk.androidrecruitmentapp.data.source.NetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AppModule.Binders::class,
        AppModule.Providers::class
    ]
)
abstract class AppModule {

    @InstallIn(SingletonComponent::class)
    @Module
    abstract class Binders {

        @Binds
        abstract fun bindContext(application: Application): Context

        @Binds
        @Singleton
        abstract fun bindNetworkDataSource(networkDataSource: NetworkDataSource): DataSource
    }

    @InstallIn(SingletonComponent::class)
    @Module
    class Providers {

        @Provides
        @Singleton
        fun provideGson(): Gson = GsonBuilder().create()
    }
}
