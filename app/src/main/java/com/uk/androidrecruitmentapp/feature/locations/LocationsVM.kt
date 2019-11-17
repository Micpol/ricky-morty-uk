package com.uk.androidrecruitmentapp.feature.locations

import androidx.lifecycle.*
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.source.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class LocationsVM : ViewModel() {

    abstract val locationsList: LiveData<List<Location>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class LocationsVMImpl @Inject constructor(

        private val repository: LocationsRepository

) : LocationsVM() {

    private val locations by lazy { MutableLiveData<Resource<List<Location>>>() }

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        locations.postValue(Resource.Loading)
        viewModelScope.launch {
            locations.postValue(repository.loadLocations())
        }
    }

    override val locationsList by lazy {
        locations.map {
            if (it is Resource.Success) {
                it.data
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

    private fun onError(resource: Resource<List<Location>>?) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

}