package com.uk.androidrecruitmentapp.data.local

data class Episodes(
        val info: Info? = null,
        val results: List<Episode> = emptyList()
)

data class Episode(
        val id: Int,
        val name: String,
        val air_date: String,
        val episode: String,
        val characters: List<String>,
        val url: String,
        val created: String
)