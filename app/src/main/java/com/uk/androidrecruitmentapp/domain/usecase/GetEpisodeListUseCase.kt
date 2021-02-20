package com.uk.androidrecruitmentapp.domain.usecase

import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import javax.inject.Inject

class GetEpisodeListUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {

    fun getEpisodes(page: Int) = networkDataSource.getEpisodes(page)
}