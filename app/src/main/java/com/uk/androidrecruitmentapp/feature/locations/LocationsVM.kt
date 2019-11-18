package com.uk.androidrecruitmentapp.feature.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.source.Resource
import com.uk.androidrecruitmentapp.feature.base.PagingViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class LocationsVM : PagingViewModel() {

    abstract val locationsList: LiveData<List<Location>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class LocationsVMImpl @Inject constructor(

        private val repository: LocationsRepository

) : LocationsVM() {

    private val locations by lazy { MutableLiveData<Resource<RickyAndMortyResponse<Location>>>() }

    init {
        loadLocations()
    }

    private fun loadLocations(page: Int? = null) {
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
        locations.map {
            it is Resource.Loading
        }
    }

    private fun onError(resource: Resource<RickyAndMortyResponse<Location>>) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

    override val toastMessage by lazy { MutableSingleLiveEvent<String>() }

    override val loadingMoreVisibility by lazy { MutableLiveData<Boolean>(false) }

    override fun loadNextPage() {
        currentPage++
        loadingMoreVisibility.postValue(isLoadingMore)
        loadLocations(currentPage)
    }
}