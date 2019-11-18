package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse

interface DataSource {
    suspend fun loadEpisodes(page: Int?): Resource<RickyAndMortyResponse<Episode>>

    suspend fun loadCharacters(page: Int?): Resource<RickyAndMortyResponse<Character>>

    suspend fun loadLocations(page: Int?): Resource<RickyAndMortyResponse<Location>>
}
