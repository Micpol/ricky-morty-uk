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

        @Binds
        @Singleton
        abstract fun bindNetworkDataSource(networkDataSource: NetworkDataSource): DataSource
    }

    @Module
    class Providers {

        @Provides
        @Singleton
        fun provideGson(): Gson = GsonBuilder().create()
    }
}
