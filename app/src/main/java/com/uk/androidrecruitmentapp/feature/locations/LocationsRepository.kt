package com.uk.androidrecruitmentapp.feature.locations

import com.uk.androidrecruitmentapp.data.source.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepository @Inject constructor(

        private val networkDataSource: DataSource

) {

    suspend fun loadLocations() = withContext(Dispatchers.IO) {
        networkDataSource.loadLocations()
    }
}
