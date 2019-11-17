package com.uk.androidrecruitmentapp.data.local

data class Locations(
        val info: Info? = null,
        val results: List<Location> = emptyList()
)

data class Location(
        val id: Int,
        val name: String,
        val type: String,
        val dimension: String,
        val url: String
)