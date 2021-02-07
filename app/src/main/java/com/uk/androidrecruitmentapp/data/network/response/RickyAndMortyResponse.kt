package com.uk.androidrecruitmentapp.data.network.response

import com.uk.androidrecruitmentapp.data.model.Info

data class RickyAndMortyResponse<T>(
    val info: Info,
    val results: List<T>
)