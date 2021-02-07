package com.uk.androidrecruitmentapp.data.network

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.ApiResponse
import com.uk.androidrecruitmentapp.data.network.response.GetEpisodesResponse
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.network.response.toResource
import com.uk.androidrecruitmentapp.data.util.RequestExecutor
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(
    private val requestExecutor: RequestExecutor,
    private val apiService: ApiService
) : NetworkDataSource {

    override suspend fun loadEpisodes(page: Int?): Resource<GetEpisodesResponse> {
        return when (val response = requestExecutor.execute(apiService.loadEpisodesAsync())) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }

    override suspend fun loadCharacters(page: Int?): Resource<RickyAndMortyResponse<Character>> {
        return when (val response = requestExecutor.execute(apiService.loadCharactersAsync())) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }

    override suspend fun loadLocations(page: Int?): Resource<RickyAndMortyResponse<Location>> {
        return when (val response = requestExecutor.execute(apiService.loadLocationsAsync(page))) {
            is ApiResponse.Success -> {
                ApiResponse.Success(response.data)
            }
            is ApiResponse.Error -> response
        }.toResource()
    }
}
