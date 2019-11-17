package com.uk.androidrecruitmentapp.data.source

import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.Episode

interface DataSource {
    suspend fun loadEpisodes(): Resource<List<Episode>>

    suspend fun loadCharacters(): Resource<List<Character>>
}
