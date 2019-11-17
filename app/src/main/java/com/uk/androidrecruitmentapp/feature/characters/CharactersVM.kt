package com.uk.androidrecruitmentapp.feature.characters

import androidx.lifecycle.*
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.source.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CharactersVM : ViewModel() {

    abstract val charactersList: LiveData<List<Character>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class CharactersVMImpl @Inject constructor(

        private val repository: CharactersRepository

) : CharactersVM() {

    private val characters by lazy { MutableLiveData<Resource<List<Character>>>() }

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        characters.postValue(Resource.Loading)
        viewModelScope.launch {
            characters.postValue(repository.loadCharacters())
        }
    }

    override val charactersList by lazy {
        characters.map {
            if (it is Resource.Success) {
                it.data
            } else {
                onError(it)
                emptyList()
            }
        }
    }
    override val progressVisibility by lazy {
        characters.map {
            it is Resource.Loading
        }
    }

    override val toastMessage by lazy { MutableSingleLiveEvent<String>() }

    private fun onError(resource: Resource<List<Character>>?) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

}