package com.uk.androidrecruitmentapp.data.local


data class Characters(
        val info: Info? = null,
        val results: List<Character> = emptyList()
)

data class Character(
        val name: String,
        val status: String,
        val species: String,
        val type: String,
        val gender: String,
        val image: String
)