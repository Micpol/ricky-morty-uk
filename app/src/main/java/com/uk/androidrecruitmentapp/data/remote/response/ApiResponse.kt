package com.uk.androidrecruitmentapp.data.remote.response

import com.uk.androidrecruitmentapp.data.source.Resource

sealed class ApiResponse<out T> {

    class Success<out T>(val data: T) : ApiResponse<T>()

    class Error(val error: Throwable) : ApiResponse<Nothing>()
}

fun <T> ApiResponse<T>.toResource(): Resource<T> {
    return when (this) {
        is ApiResponse.Success -> Resource.Success(data)
        is ApiResponse.Error -> Resource.Error(error)
    }
}
