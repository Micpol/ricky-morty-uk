package com.uk.androidrecruitmentapp.feature

import com.uk.androidrecruitmentapp.feature.characters.CharactersFragment
import com.uk.androidrecruitmentapp.feature.episodes.EpisodesFragment
import com.uk.androidrecruitmentapp.feature.locations.LocationsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeModule {

    @ContributesAndroidInjector
    abstract fun contributeEpisodesFragment(): EpisodesFragment

    @ContributesAndroidInjector
    abstract fun contributeCharactersFragment(): CharactersFragment

    @ContributesAndroidInjector
    abstract fun contributeLocationsFragment(): LocationsFragment
}