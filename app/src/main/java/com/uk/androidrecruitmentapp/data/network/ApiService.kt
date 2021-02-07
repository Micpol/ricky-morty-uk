package com.uk.androidrecruitmentapp.data.network

import com.uk.androidrecruitmentapp.data.model.Character
import com.uk.androidrecruitmentapp.data.model.Episode
import com.uk.androidrecruitmentapp.data.model.Location
import com.uk.androidrecruitmentapp.data.network.response.RickyAndMortyResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("episode/")
    fun loadEpisodesAsync(@Query("page") page: Int? = null): Deferred<Response<RickyAndMortyResponse<Episode>>>

    @GET("character/")
    fun loadCharactersAsync(@Query("page") page: Int? = null): Deferred<Response<RickyAndMortyResponse<Character>>>

    @GET("location/")
    fun loadLocationsAsync(@Query("page") page: Int? = null): Deferred<Response<RickyAndMortyResponse<Location>>>

}