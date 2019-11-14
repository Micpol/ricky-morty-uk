package com.uk.androidrecruitmentapp.data.source

sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    class Success<out T>(val data: T) : Resource<T>()

    class Error(val error: Throwable) : Resource<Nothing>()
}
