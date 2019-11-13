package com.uk.androidrecruitmentapp.di.contribute

import com.uk.androidrecruitmentapp.feature.episodes.HomeActivity
import com.uk.androidrecruitmentapp.feature.episodes.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributeModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}
