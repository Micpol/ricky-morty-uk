package com.uk.androidrecruitmentapp.domain.usecase

import com.uk.androidrecruitmentapp.data.network.response.GetLocationsResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import javax.inject.Inject

class GetLocationListUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getLocations(page: Int): Resource<GetLocationsResponse> = networkDataSource.loadLocations(page)

}