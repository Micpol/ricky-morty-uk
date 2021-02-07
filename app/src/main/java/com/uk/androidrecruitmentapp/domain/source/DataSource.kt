package com.uk.androidrecruitmentapp.domain.source

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import com.uk.androidrecruitmentapp.domain.model.Resource

interface DataSource {
    suspend fun loadEpisodes(page: Int?): Resource<RickyAndMortyResponse<Episode>>

    suspend fun loadCharacters(page: Int?): Resource<RickyAndMortyResponse<Character>>

    suspend fun loadLocations(page: Int?): Resource<RickyAndMortyResponse<Location>>
}
