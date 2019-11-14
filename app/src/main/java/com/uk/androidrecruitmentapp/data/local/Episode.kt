package com.uk.androidrecruitmentapp.data.local

data class Episodes(
    val info: Info? = null,
    val results: List<Result> = emptyList()
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)

data class Result(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
)