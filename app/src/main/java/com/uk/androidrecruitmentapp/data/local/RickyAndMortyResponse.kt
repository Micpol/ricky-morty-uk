package com.uk.androidrecruitmentapp.data.local

data class RickyAndMortyResponse<T>(
        val info: Info,
        val results: List<T>
)