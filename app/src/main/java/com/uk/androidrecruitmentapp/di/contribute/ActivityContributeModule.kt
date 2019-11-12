package com.uk.androidrecruitmentapp.di.contribute

import com.uk.androidrecruitmentapp.ui.EpisodesActivity
import com.uk.androidrecruitmentapp.ui.EpisodesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityContributeModule {

    @ContributesAndroidInjector(modules = [EpisodesModule::class])
    abstract fun contributeHomeActivity(): EpisodesActivity
}
