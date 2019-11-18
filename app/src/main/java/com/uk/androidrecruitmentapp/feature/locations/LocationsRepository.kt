package com.uk.androidrecruitmentapp.feature.locations

import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.data.source.DataSource
import com.uk.androidrecruitmentapp.data.source.Resource
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
