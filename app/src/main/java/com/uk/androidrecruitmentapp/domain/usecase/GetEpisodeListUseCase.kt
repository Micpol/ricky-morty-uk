package com.uk.androidrecruitmentapp.domain.usecase

import com.uk.androidrecruitmentapp.data.network.response.GetEpisodesResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import javax.inject.Inject

class GetEpisodeListUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getEpisodes(page: Int): Resource<GetEpisodesResponse> = networkDataSource.loadEpisodes(page)
}