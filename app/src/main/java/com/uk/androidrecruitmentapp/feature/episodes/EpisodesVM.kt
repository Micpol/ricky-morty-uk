package com.uk.androidrecruitmentapp.feature.episodes

import androidx.lifecycle.*
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.source.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class EpisodesVM : ViewModel() {

    abstract val episodesList: LiveData<List<Episode>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class EpisodesVMImpl @Inject constructor(

        private val repository: EpisodesRepository

) : EpisodesVM() {

    private val episodes by lazy { MutableLiveData<Resource<List<Episode>>>() }

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        episodes.postValue(Resource.Loading)
        viewModelScope.launch {
            episodes.postValue(repository.loadEpisodes())
        }
    }

    override val episodesList by lazy {
        episodes.map {
            if (it is Resource.Success) {
                it.data
            } else {
                onError(it)
                emptyList()
            }
        }
    }
    override val progressVisibility by lazy {
        episodes.map {
            it is Resource.Loading
        }
    }

    override val toastMessage by lazy { MutableSingleLiveEvent<String>() }

    private fun onError(resource: Resource<List<Episode>>?) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

}