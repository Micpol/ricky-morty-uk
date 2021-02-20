package com.uk.androidrecruitmentapp.data.network

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.ApiResponse
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.network.response.toResource
import com.uk.androidrecruitmentapp.data.util.RequestExecutor
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class NetworkDataSourceImpl @Inject constructor(
    private val requestExecutor: RequestExecutor,
    private val apiService: ApiService,
) : NetworkDataSource {

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

    @FlowPreview
    override fun getEpisodes(page: Int?): Flow<List<Episode>> {
        return flow {
            val initial = apiService.getEpisodes().results
            emit(initial)
        }
    }

}