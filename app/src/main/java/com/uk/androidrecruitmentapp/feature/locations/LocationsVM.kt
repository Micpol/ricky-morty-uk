package com.uk.androidrecruitmentapp.feature.locations

import androidx.lifecycle.*
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.source.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class LocationsVM : ViewModel() {
    abstract fun onListScrolled(lastCompletelyVisibleItemPosition: Int, itemCount: Int)

    abstract val locationsList: LiveData<List<Location>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>
    abstract val loadingMoreVisibility: LiveData<Boolean>

}

class LocationsVMImpl @Inject constructor(

        private val repository: LocationsRepository

) : LocationsVM() {

    private val locations by lazy { MutableLiveData<Resource<RickyAndMortyResponse<Location>>>() }

    private var isLoadingMore = false
    private var currentPage = 1
    private var isThereNextPage = true

    init {
        loadEpisodes(null)
    }

    private fun loadEpisodes(page: Int? = null) {
        locations.postValue(Resource.Loading)
        viewModelScope.launch {
            val loadLocations = repository.loadLocations(page)
            locations.postValue(loadLocations)
        }
    }

    override val locationsList by lazy {
        locations.map {
            if (it is Resource.Success) {
                isThereNextPage = it.data.info.next.isNotEmpty()
                loadingMoreVisibility.postValue(false)
                isLoadingMore = false
                it.data.results
            } else {
                onError(it)
                emptyList()
            }
        }
    }

    override val progressVisibility by lazy {
        locations.map {
            it is Resource.Loading
        }
    }

    override val toastMessage by lazy { MutableSingleLiveEvent<String>() }

    override val loadingMoreVisibility by lazy { MutableLiveData<Boolean>(false) }

    private fun onError(resource: Resource<RickyAndMortyResponse<Location>>) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

    override fun onListScrolled(lastCompletelyVisibleItemPosition: Int, itemCount: Int) {
        if (isLoadingMore || !isThereNextPage) return
        if (lastCompletelyVisibleItemPosition == itemCount - 1) {
            isLoadingMore = true
            loadingMoreVisibility.postValue(isLoadingMore)
            loadNextPage()
        }
    }

    private fun loadNextPage() {
        currentPage++
        isLoadingMore = false
        loadEpisodes(currentPage)
    }
}