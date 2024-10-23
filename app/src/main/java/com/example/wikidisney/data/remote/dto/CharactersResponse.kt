package com.example.wikidisney.data.remote.dto

data class CharactersResponse(
    val data: List<CharacterInfo>,
    val info: Info
)