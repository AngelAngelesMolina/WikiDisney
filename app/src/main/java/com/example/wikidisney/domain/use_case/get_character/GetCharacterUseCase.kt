package com.example.wikidisney.domain.use_case.get_character

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repositoryImpl: CharacterRepositoryImpl) {
     /*suspend operator fun invoke(characterId: Int): Resource<CharacterResponse> {
        val response = try {
            repositoryImpl.getCharacterInfoAlt(characterId)
        } catch (e: Exception) {
            return Resource.Error("An unknow error occured")
        }
        return Resource.Success(response)
    }*/
    suspend operator fun invoke(characterId: Int): Resource<CharacterEntity> {
        return repositoryImpl.getCharacterInfoRoom(characterId)
    }
}