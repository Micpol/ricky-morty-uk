package com.uk.androidrecruitmentapp.data.remote

import com.uk.androidrecruitmentapp.data.local.Character
import com.uk.androidrecruitmentapp.data.local.Episode
import com.uk.androidrecruitmentapp.data.local.Location
import com.uk.androidrecruitmentapp.data.local.RickyAndMortyResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("episode/")
    fun loadEpisodesAsync(): Deferred<Response<RickyAndMortyResponse<Episode>>>

    @GET("character/")
    fun loadCharactersAsync(): Deferred<Response<RickyAndMortyResponse<Character>>>

    @GET("location/")
    fun loadLocationsAsync(): Deferred<Response<RickyAndMortyResponse<Location>>>

}