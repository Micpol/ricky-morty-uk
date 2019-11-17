package com.uk.androidrecruitmentapp.di.contribute

import com.uk.androidrecruitmentapp.feature.HomeActivity
import com.uk.androidrecruitmentapp.feature.HomeModule
import com.uk.androidrecruitmentapp.ui.SplashActivity
import com.uk.androidrecruitmentapp.ui.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributeModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributeSplashActivity(): SplashActivity
}
