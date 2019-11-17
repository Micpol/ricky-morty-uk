package com.uk.androidrecruitmentapp.data.remote

import com.uk.androidrecruitmentapp.data.local.Characters
import com.uk.androidrecruitmentapp.data.local.Episodes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("episode/")
    fun loadEpisodesAsync(): Deferred<Response<Episodes>>

    @GET("character/")
    fun loadCharactersAsync(): Deferred<Response<Characters>>

}