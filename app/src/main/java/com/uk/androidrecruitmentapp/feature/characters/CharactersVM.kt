package com.uk.androidrecruitmentapp.feature.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.data.livedata.SingleLiveEvent
import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.source.Resource
import com.uk.androidrecruitmentapp.feature.base.PagingViewModel
import kotlinx.coroutines.launch

abstract class CharactersVM : PagingViewModel() {

    abstract val charactersList: LiveData<List<Character>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

class CharactersVMImpl @ViewModelInject constructor(
    private val repository: CharactersRepository
) : CharactersVM() {

    private val characters by lazy { MutableLiveData<Resource<RickyAndMortyResponse<Character>>>() }

    init {
        loadCharacters()
    }

    private fun loadCharacters(page: Int? = null) {
        characters.postValue(Resource.Loading)
        viewModelScope.launch {
            characters.postValue(repository.loadCharacters())
        }
    }

    override val charactersList by lazy {
        characters.map {
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
        characters.map {
            it is Resource.Loading
        }
    }

    override val toastMessage by lazy { MutableSingleLiveEvent<String>() }

    private fun onError(resource: Resource<RickyAndMortyResponse<Character>>?) {
        if (resource is Resource.Error) {
            toastMessage.postValue(resource.error.message)
        }
    }

    override val loadingMoreVisibility by lazy { MutableLiveData<Boolean>(false) }

    override fun loadNextPage() {
        currentPage++
        loadingMoreVisibility.postValue(isLoadingMore)
        loadCharacters(currentPage)
    }

}