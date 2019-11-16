package com.uk.androidrecruitmentapp.di.contribute

import com.uk.androidrecruitmentapp.feature.HomeActivity
import com.uk.androidrecruitmentapp.feature.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributeModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}
