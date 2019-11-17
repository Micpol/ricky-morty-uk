package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Result
import com.uk.androidrecruitmentapp.data.remote.ApiService
import com.uk.androidrecruitmentapp.data.remote.RequestExecutor
import com.uk.androidrecruitmentapp.data.remote.response.ApiResponse
import com.uk.androidrecruitmentapp.data.remote.response.toResource
import javax.inject.Inject

class NetworkDataSource @Inject constructor(

        private val requestExecutor: RequestExecutor,
        private val apiService: ApiService

) : DataSource {

    override suspend fun loadEpisodes(): Resource<List<Result>> {
        return when (val value = requestExecutor.execute(apiService.loadEpisodesAsync())) {
            is ApiResponse.Success -> {
                val list = value.data.results
                ApiResponse.Success(list)
            }
            is ApiResponse.Error -> value
        }.toResource()
    }
}
