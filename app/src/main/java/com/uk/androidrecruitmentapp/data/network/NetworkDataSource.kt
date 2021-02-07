package com.uk.androidrecruitmentapp.data.network

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.ApiResponse
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.network.response.toResource
import com.uk.androidrecruitmentapp.data.util.RequestExecutor
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.DataSource
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

    override suspend fun loadCharacters(page: Int?): Resource<RickyAndMortyResponse<Character>> {
        return when (val response = requestExecutor.execute(apiService.loadCharactersAsync())) {
            is ApiResponse.Success -> {
                val list = response.data
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
