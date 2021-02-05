package com.uk.androidrecruitmentapp.di

import com.uk.androidrecruitmentapp.di.module.AppModule
import com.uk.androidrecruitmentapp.di.module.NetworkModule
import com.uk.androidrecruitmentapp.di.viewModel.ViewModelModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        AppModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
//
//    @Component.Builder
//    interface Builder {
//
//        @BindsInstance
//        fun application(application: Application): Builder
//
//        fun build(): AppComponent
//    }
}
