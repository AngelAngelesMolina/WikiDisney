package com.example.wikidisney.data.remote

import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.remote.dto.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DisneyApi {
    @GET("character")
    suspend fun getCharactersList(
        @Query("page") page: Int,
    ): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterInfo(
        @Path("id") id: Int
    ): CharacterResponse
}