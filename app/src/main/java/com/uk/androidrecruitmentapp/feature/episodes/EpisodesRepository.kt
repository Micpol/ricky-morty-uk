package com.uk.androidrecruitmentapp.feature.episodes

import com.uk.androidrecruitmentapp.data.source.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val networkDataSource: DataSource
) {

    suspend fun loadEpisodes(page: Int? = null) = withContext(Dispatchers.IO) {
        networkDataSource.loadEpisodes(page)
    }
}
