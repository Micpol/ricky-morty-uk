package com.uk.androidrecruitmentapp.data.remote

import com.uk.androidrecruitmentapp.data.remote.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RequestExecutor @Inject constructor() {

    suspend fun <T : Any> execute(call: Deferred<Response<T>>): ApiResponse<T> {
        return try {
            val response = call.await()
            if (response.isSuccessful) {
                @Suppress("UNCHECKED_CAST")
                ApiResponse.Success(response.body() ?: Unit as T)
            } else {
                ApiResponse.Error(HttpException(response))
            }
        } catch (httpException: HttpException) {
            ApiResponse.Error(httpException)
        } catch (throwable: Throwable) {
            ApiResponse.Error(throwable)
        }
    }
}
