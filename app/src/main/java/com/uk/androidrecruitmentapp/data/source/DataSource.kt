package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.local.Location

interface DataSource {
    suspend fun loadEpisodes(): Resource<List<Episode>>

    suspend fun loadCharacters(): Resource<List<Character>>

    suspend fun loadLocations(): Resource<List<Location>>
}
