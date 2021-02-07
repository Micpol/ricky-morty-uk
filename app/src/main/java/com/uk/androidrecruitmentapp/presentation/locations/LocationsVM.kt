package com.uk.androidrecruitmentapp.presentation.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.usecase.GetLocationListUseCase
import com.uk.androidrecruitmentapp.presentation.base.PagingViewModel
import com.uk.androidrecruitmentapp.presentation.utils.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class LocationsVM : PagingViewModel() {

    abstract val locationsList: LiveData<List<Location>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

@HiltViewModel
class LocationsVMImpl @Inject constructor(
    private val getLocationsUseCase: GetLocationListUseCase
) : LocationsVM() {

    private val locations by lazy { MutableLiveData<Resource<RickyAndMortyResponse<Location>>>() }

    init {
        loadLocations(currentPage)
    }

    private fun loadLocations(page: Int) {
        locations.postValue(Resource.Loading)
        viewModelScope.launch {
            val loadLocations = getLocationsUseCase.getLocations(page)
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