package com.uk.androidrecruitmentapp.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module


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
    class Providers
}