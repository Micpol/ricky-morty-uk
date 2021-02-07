package com.uk.androidrecruitmentapp.presentation.locations

import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepository @Inject constructor(
    private val networkDataSource: DataSource
) {

    suspend fun loadLocations(page: Int?): Resource<RickyAndMortyResponse<Location>> {
        return withContext(Dispatchers.IO) {
            networkDataSource.loadLocations(page)
        }
    }
}
