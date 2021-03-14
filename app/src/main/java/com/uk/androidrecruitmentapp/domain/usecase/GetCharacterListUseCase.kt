package com.uk.androidrecruitmentapp.domain.usecase

import com.uk.androidrecruitmentapp.data.network.response.GetCharactersResponse
import com.uk.androidrecruitmentapp.domain.model.Resource
import com.uk.androidrecruitmentapp.domain.source.NetworkDataSource
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun getCharacters(page: Int): Resource<GetCharactersResponse> = networkDataSource.loadCharacters(page)
}