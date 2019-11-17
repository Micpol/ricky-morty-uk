package com.uk.androidrecruitmentapp.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uk.androidrecruitmentapp.feature.characters.CharactersVM
import com.uk.androidrecruitmentapp.feature.characters.CharactersVMImpl
import com.uk.androidrecruitmentapp.feature.episodes.EpisodesVM
import com.uk.androidrecruitmentapp.feature.episodes.EpisodesVMImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesVM::class)
    abstract fun provideEpisodesVM(vm: EpisodesVMImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersVM::class)
    abstract fun provideCharactersVM(vm: CharactersVMImpl): ViewModel

}
