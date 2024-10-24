package com.example.wikidisney.data.remote.dto

import com.example.wikidisney.data.database.entities.CharacterEntity

data class CharacterInfo(
    val __v: Int,
    val _id: Int,
    val allies: List<Any>,
    val createdAt: String,
    val enemies: List<Any>,
    val films: List<String>,
    val imageUrl: String?,
    val name: String,
    val parkAttractions: List<Any>,
    val shortFilms: List<Any>,
    val sourceUrl: String,
    val tvShows: List<String>,
    val updatedAt: String,
    val url: String,
    val videoGames: List<String>
)
fun CharacterInfo.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        id = this._id,
        name = this.name,
        imageUrl = this.imageUrl ?: "",
        films = this.films,
        tvShows = this.tvShows,
        videoGames = this.videoGames
    )
}