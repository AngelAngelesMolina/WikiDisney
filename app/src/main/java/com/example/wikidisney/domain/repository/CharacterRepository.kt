package com.example.wikidisney.domain.repository

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.remote.dto.CharactersResponse
import com.example.wikidisney.data.remote.dto.toCharacterEntity

interface CharacterRepository {
    suspend fun getCharacterList(page: Int): CharactersResponse
    suspend fun getCharacterListRoom(page: Int):  Resource<List<CharacterEntity>>
    suspend fun getCharacterInfoAlt(idChar: Int): CharacterResponse

    suspend fun getCharacterInfo(idCharacter: Int): Resource<CharacterResponse>
    suspend fun getCharacterInfoRoom(characterId: Int): Resource<CharacterEntity>
}