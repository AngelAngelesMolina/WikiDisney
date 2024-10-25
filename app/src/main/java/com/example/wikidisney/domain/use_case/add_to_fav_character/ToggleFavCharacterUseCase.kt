package com.example.wikidisney.domain.use_case.add_to_fav_character

import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class ToggleFavCharacterUseCase @Inject constructor(
    private val repositoryImpl: CharacterRepositoryImpl
) {
    suspend operator fun invoke(character: CharacterEntity) {
        character.isFavorite = !character.isFavorite
        repositoryImpl.updateCharacter(character)
    }
}