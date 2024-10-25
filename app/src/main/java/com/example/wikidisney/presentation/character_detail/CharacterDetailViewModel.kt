package com.example.wikidisney.presentation.character_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.remote.dto.CharacterInfo
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import com.example.wikidisney.domain.use_case.add_to_fav_character.ToggleFavCharacterUseCase
import com.example.wikidisney.domain.use_case.get_character.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase,
private val repository: CharacterRepositoryImpl,
    private val toggleFavCharacterUseCase: ToggleFavCharacterUseCase
) :
    ViewModel() {
    var characterDetail = mutableStateOf<CharacterEntity?>(null)
        private set
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    suspend fun getCharacterDetailAlt(characterId: Int): Resource<CharacterEntity> {
        isLoading.value = true
        return try {
            // Llama al caso de uso para obtener el detalle del personaje
            val result = getCharacterUseCase(characterId)
            if (result is Resource.Success) {
                characterDetail.value = result.data
            }
            isLoading.value = false
            result // Devuelve el resultado obtenido del use case
        } catch (e: Exception) {
            isLoading.value = false
            Resource.Error("Error al obtener los detalles del personaje") // Manejo de error
        }
    }
    fun toggleFavorite() {
        viewModelScope.launch {
            characterDetail.value?.let { character ->
                toggleFavCharacterUseCase(character)
                // Cambia el estado de favorito
                characterDetail.value = character.copy(isFavorite = !character.isFavorite) // Forzar actualización del estado
            }
        }
    }
    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val result = getCharacterUseCase(characterId)
            when (result) {
                is Resource.Success -> {
                    characterDetail.value = result.data
                    loadError.value = ""
                }
                is Resource.Error -> {
                    loadError.value = result.message ?: "Unknown error"
                }

                else -> {}
            }
            isLoading.value = false
        }
    }

   suspend fun getCharacterInfoAlt(charId: Int): Resource<CharacterResponse> {
       return try {
           val response = repository.getCharacterInfoAlt(charId)
           Resource.Success(response) // Devuelve la data en un Resource.Success si la llamada es exitosa
       } catch (e: Exception) {
           Resource.Error("An unknown error occurred") // Maneja cualquier excepción y devuelve un Resource.Error
       }
   }

   suspend fun getCharacterInfo(charId: Int): Resource<CharacterResponse> {
       return repository.getCharacterInfo(charId)
   }
}