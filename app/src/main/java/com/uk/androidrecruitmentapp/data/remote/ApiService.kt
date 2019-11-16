package com.uk.androidrecruitmentapp.data.remote

import com.uk.androidrecruitmentapp.data.local.Episodes
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("episode/")
    suspend fun loadEpisodes(): Response<Episodes>

    @GET("episode/")
    fun getPeople(): Single<Episodes>
}