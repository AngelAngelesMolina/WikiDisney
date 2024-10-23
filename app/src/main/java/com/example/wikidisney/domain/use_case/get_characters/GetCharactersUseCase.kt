package com.example.wikidisney.domain.use_case.get_characters

import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.remote.dto.CharactersResponse
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import timber.log.Timber
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repositoryImpl: CharacterRepositoryImpl) {
    suspend operator fun invoke(page: Int): Resource<CharactersResponse> {
        val response = try {
            repositoryImpl.getCharacterList(page)
        } catch (e: Exception) {
            return Resource.Error("An unknow error occured")
        }
        return Resource.Success(response)
    }
}