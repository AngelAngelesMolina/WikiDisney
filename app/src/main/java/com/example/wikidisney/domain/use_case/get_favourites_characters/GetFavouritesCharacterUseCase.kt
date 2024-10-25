package com.example.wikidisney.domain.use_case.get_favourites_characters

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetFavouritesCharacterUseCase @Inject constructor(private val repositoryImpl: CharacterRepositoryImpl) {
    suspend operator fun invoke(): List<CharacterEntity> {
        return repositoryImpl.getFavoriteCharacters()
    }
}