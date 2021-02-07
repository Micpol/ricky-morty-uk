package com.uk.androidrecruitmentapp.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.presentation.utils.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class SplashVM : ViewModel() {

    abstract val splashLoading: SingleLiveEvent<Boolean>

}

@HiltViewModel
class SplashVMImpl @Inject constructor() : SplashVM() {

    override val splashLoading by lazy { MutableSingleLiveEvent<Boolean>() }

    init {
        startLoadingSplash()
    }

    private fun startLoadingSplash() {
        viewModelScope.launch {
            load()
            splashLoading.postValue(true)
        }
    }

    private suspend fun load() {
        delay(2_000L)
    }
}