package com.uk.androidrecruitmentapp.data.remote

import com.uk.androidrecruitmentapp.data.remote.response.ApiResponse
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RequestExecutor @Inject constructor() {

    fun <T : Any> execute(call: Response<T>): ApiResponse<T> {
        return try {
            if (call.isSuccessful) {
                @Suppress("UNCHECKED_CAST")
                ApiResponse.Success(call.body() ?: Unit as T)
            } else {
                ApiResponse.Error(HttpException(call))
            }
        } catch (httpException: HttpException) {
            ApiResponse.Error(httpException)
        } catch (throwable: Throwable) {
            ApiResponse.Error(throwable)
        }
    }
}
