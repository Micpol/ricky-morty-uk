package com.uk.androidrecruitmentapp.di

import android.app.Application
import com.uk.androidrecruitmentapp.ARApplication
import com.uk.androidrecruitmentapp.di.contribute.ActivityContributeModule
import com.uk.androidrecruitmentapp.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            ActivityContributeModule::class
        ]
)
interface AppComponent : AndroidInjector<ARApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
