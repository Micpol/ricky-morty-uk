package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.remote.ApiService
import com.uk.androidrecruitmentapp.data.remote.RequestExecutor
import com.uk.androidrecruitmentapp.data.remote.response.ApiResponse
import com.uk.androidrecruitmentapp.data.remote.response.toResource
import javax.inject.Inject

class NetworkDataSource @Inject constructor(

        private val requestExecutor: RequestExecutor,
        private val apiService: ApiService

) : DataSource {

    override suspend fun loadEpisodes(page: Int?): Resource<RickyAndMortyResponse<Episode>> {
        return when (val response = requestExecutor.execute(apiService.loadEpisodesAsync())) {
            is ApiResponse.Success -> {
                val list = response.data
                ApiResponse.Success(list)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }

    override suspend fun loadCharacters(): Resource<List<Character>> {
        return when (val response = requestExecutor.execute(apiService.loadCharactersAsync())) {
            is ApiResponse.Success -> {
                val list = response.data.results
                ApiResponse.Success(list)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }

    override suspend fun loadLocations(page: Int?): Resource<RickyAndMortyResponse<Location>> {
        return when (val response = requestExecutor.execute(apiService.loadLocationsAsync(page))) {
            is ApiResponse.Success -> {
                val list = response.data
                ApiResponse.Success(list)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }
}
