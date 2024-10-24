package com.example.wikidisney.domain.use_case.get_characters

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repositoryImpl: CharacterRepositoryImpl) {
    suspend operator fun invoke(page: Int): Resource<List<CharacterEntity>> {
        return try {
            repositoryImpl.getCharacterListRoom(page)
        } catch (e: Exception) {
            Resource.Error("An unknown error occurred")
        }
    }
}