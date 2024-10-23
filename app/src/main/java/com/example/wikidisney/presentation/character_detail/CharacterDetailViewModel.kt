package com.example.wikidisney.presentation.character_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.remote.dto.CharacterInfo
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.data.repository.CharacterRepositoryImpl
import com.example.wikidisney.domain.use_case.get_character.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase,
private val repository: CharacterRepositoryImpl

) :
    ViewModel() {
   /* lateinit var currentCharacter: Resource<CharacterResponse>

    fun getCharacterInfo(charId: Int): Resource<CharacterResponse> {
        viewModelScope.launch {
            currentCharacter = Resource.Loading() // Estado de carga inicial

            val result = getCharacterUseCase(charId)
            currentCharacter = result;
            currentCharacter = when (result) {
                is Resource.Success -> result
                is Resource.Error -> result
                else -> Resource.Error("Unknown error")
            }
            Timber.tag("VIEWMODEL").w(currentCharacter.toString())
            Timber.tag("VIEWMODEL").w(result.toString())

        }
        Timber.tag("VIEWMODEL").w(currentCharacter.toString())
        return currentCharacter
    }*/
    // Estado de los datos de detalle del personaje
    /*var characterDetail = mutableStateOf<CharacterResponse?>(null)
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    // Iniciar la petición de detalle con el id del personaje
    fun loadCharacterDetail(charId: Int) {
        viewModelScope.launch {
            isLoading.value = true
            val result = getCharacterUseCase(charId)
            when (result) {
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
                is Resource.Loading -> {
                    // Estado de carga si lo necesitas
                }
                is Resource.Success -> {
                    characterDetail.value = result.data
                    loadError.value = ""
                    isLoading.value = false
                }
            }
        }
    }*/
   // Estado de los datos de detalle del personaje
   /*private val _characterDetail = MutableStateFlow<CharacterResponse?>(null)
    val characterDetail: StateFlow<CharacterResponse?> = _characterDetail

    private val _loadError = MutableStateFlow("")
    val loadError: StateFlow<String> = _loadError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Iniciar la petición de detalle con el id del personaje
    fun loadCharacterDetail(charId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getCharacterUseCase(charId)
            when (result) {
                is Resource.Error -> {
                    _loadError.value = result.message ?: "Unknown error"
                    _isLoading.value = false
                }
                is Resource.Loading -> {
                    // Estado de carga si lo necesitas
                }
                is Resource.Success -> {
                    _characterDetail.value = result.data
                    _loadError.value = ""
                    _isLoading.value = false
                }
            }
        }
    }*/
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