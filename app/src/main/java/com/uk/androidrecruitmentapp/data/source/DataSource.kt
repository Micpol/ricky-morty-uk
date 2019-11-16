package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Result

interface DataSource {
    suspend fun loadEpisodes(): Resource<List<Result>>
}
