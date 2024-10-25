package com.example.wikidisney.domain.use_case.get_character

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repositoryImpl: CharacterRepositoryImpl) {
    suspend operator fun invoke(characterId: Int): Resource<CharacterEntity> {
        return repositoryImpl.getCharacterInfoRoom(characterId)
    }
}