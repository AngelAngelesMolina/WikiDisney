package com.example.wikidisney.data.repository

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.remote.DisneyApi
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.remote.dto.CharactersResponse
import com.example.wikidisney.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val api: DisneyApi) :
    CharacterRepository {
    override suspend fun getCharacterList(page: Int): CharactersResponse {
        return api.getCharactersList(page)
    }

    override suspend fun getCharacterInfoAlt(idChar: Int): CharacterResponse {
        return api.getCharacterInfo(idChar)
    }


    override suspend fun getCharacterInfo(idCharacter: Int): Resource<CharacterResponse> {
        val response = try {
            api.getCharacterInfo(idCharacter)
        } catch (e: Exception) {
            return Resource.Error("An unknow error occured")
        }
        return Resource.Success(response)

    }

}