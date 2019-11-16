package com.uk.androidrecruitmentapp.feature.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Result
import com.uk.androidrecruitmentapp.data.source.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class EpisodesVM : ViewModel() {

    abstract val episodesList: LiveData<List<Result>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class EpisodesVMImpl @Inject constructor(

        private val repository: EpisodesRepository

) : EpisodesVM() {

    private val episodes by lazy { MutableLiveData<Resource<List<Result>>>() }

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        episodes.postValue(Resource.Loading)
        viewModelScope.launch {
            episodes.postValue(repository.loadEpisodes())
        }
    }


    override val episodesList = MutableLiveData<List<Result>>()
    override val progressVisibility = MutableLiveData<Boolean>()
    override val toastMessage = MutableSingleLiveEvent<String>()

}