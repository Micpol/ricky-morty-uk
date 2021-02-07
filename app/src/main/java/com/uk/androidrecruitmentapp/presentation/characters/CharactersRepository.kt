package com.uk.androidrecruitmentapp.presentation.characters

import com.uk.androidrecruitmentapp.domain.source.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val networkDataSource: DataSource
) {

    suspend fun loadCharacters(page: Int? = null) = withContext(Dispatchers.IO) {
        networkDataSource.loadCharacters(page)
    }
}
