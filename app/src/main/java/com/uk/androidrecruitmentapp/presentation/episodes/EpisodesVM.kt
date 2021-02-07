package com.uk.androidrecruitmentapp.presentation.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.network.response.GetEpisodesResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.usecase.GetEpisodeListUseCase
import com.uk.androidrecruitmentapp.presentation.base.PagingViewModel
import com.uk.androidrecruitmentapp.presentation.utils.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class EpisodesVM : PagingViewModel() {

    abstract val episodesList: LiveData<List<Episode>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

@HiltViewModel
class EpisodesVMImpl @Inject constructor(
    private val getEpisodesUseCase: GetEpisodeListUseCase
) : EpisodesVM() {

    private val episodes by lazy { MutableLiveData<Resource<GetEpisodesResponse>>() }

    init {
        loadEpisodes(currentPage)
    }

    private fun loadEpisodes(page: Int) {
        episodes.postValue(Resource.Loading)
        viewModelScope.launch {
            val loadEpisodes = getEpisodesUseCase.getEpisodes(page)
            episodes.postValue(loadEpisodes)
        }
    }

    override val episodesList by lazy {
        episodes.map {
            if (it is Resource.Success) {
                isThereNextPage = !it.data.info.next.isNullOrEmpty()
                isLoadingMore = false
                loadingMoreVisibility.postValue(isLoadingMore)
                it.data.results
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

    private fun onError(resource: Resource<GetEpisodesResponse>) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

    override val loadingMoreVisibility by lazy { MutableLiveData(false) }

    override fun loadNextPage() {
        currentPage++
        loadingMoreVisibility.postValue(isLoadingMore)
        loadEpisodes(currentPage)
    }
}