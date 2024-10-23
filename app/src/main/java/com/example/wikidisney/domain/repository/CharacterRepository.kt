package com.example.wikidisney.domain.repository

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.remote.dto.CharactersResponse

interface CharacterRepository {
    suspend fun getCharacterList(page: Int): CharactersResponse

    suspend fun getCharacterInfo(idCharacter: Int): Resource<CharacterResponse>
}