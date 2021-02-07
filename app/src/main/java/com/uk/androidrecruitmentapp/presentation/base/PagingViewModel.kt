package com.uk.androidrecruitmentapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class PagingViewModel : ViewModel() {

    internal var isLoadingMore = false
    internal var currentPage = 1
    internal var isThereNextPage = true

    fun onListScrolled(lastCompletelyVisibleItemPosition: Int, itemCount: Int) {
        if (isLoadingMore || !isThereNextPage) return
        if (lastCompletelyVisibleItemPosition == itemCount - 1) {
            isLoadingMore = true
            loadNextPage()
        }
    }

    abstract val loadingMoreVisibility: LiveData<Boolean>

    internal abstract fun loadNextPage()

}
