package com.uk.androidrecruitmentapp.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.presentation.base.PagingViewModel
import com.uk.androidrecruitmentapp.presentation.utils.livedata.MutableSingleLiveEvent
import com.uk.androidrecruitmentapp.presentation.utils.livedata.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class CharactersVM : PagingViewModel() {

    abstract val charactersList: LiveData<List<Character>>
    abstract val progressVisibility: LiveData<Boolean>
    abstract val toastMessage: SingleLiveEvent<String>

}

@HiltViewModel
class CharactersVMImpl @Inject constructor(
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