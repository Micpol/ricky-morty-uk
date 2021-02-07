package com.uk.androidrecruitmentapp.data.network.response

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.model.Info
import com.uk.androidrecruitmentapp.data.model.Location

data class RickyAndMortyResponse<T>(
    val info: Info,
    val results: List<T>
)

typealias GetCharactersResponse = RickyAndMortyResponse<Character>
typealias GetEpisodesResponse = RickyAndMortyResponse<Episode>
typealias GetLocationsResponse = RickyAndMortyResponse<Location>
