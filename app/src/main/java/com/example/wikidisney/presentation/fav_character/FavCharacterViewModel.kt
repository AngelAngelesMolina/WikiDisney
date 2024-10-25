package com.example.wikidisney.presentation.fav_character

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.domain.model.CharacterListEntry
import com.example.wikidisney.domain.use_case.get_characters.GetCharactersUseCase
import com.example.wikidisney.domain.use_case.get_favourites_characters.GetFavouritesCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FavCharacterViewModel @Inject constructor(private val getFavouritesCharacterUseCase: GetFavouritesCharacterUseCase) : ViewModel() {

  /*  var favoriteCharacters = mutableStateOf<List<CharacterListEntry>>(emptyList())
        private set
    var isLoading = mutableStateOf(false)
    var loadError = mutableStateOf("")
    init {
        loadFavoriteCharacters()
    }
    fun loadFavoriteCharacters() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Obtener los personajes favoritos de la base de datos
                val characters = getFavouritesCharacterUseCase()
                // Mapear los resultados a CharacterListEntry
                favoriteCharacters.value = characters.map { characterEntity ->
                    CharacterListEntry(
                        characterName = characterEntity.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        },
                        imageUrl = characterEntity.imageUrl,
                        id = characterEntity.id
                    )
                }
            } catch (e: Exception) {
                loadError.value = "Error al cargar personajes favoritos: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }

    }*/
  var favoriteCharacters = mutableStateOf<List<CharacterListEntry>>(emptyList())
      private set
    var isLoading = mutableStateOf(false)
        private set
    var loadError = mutableStateOf("")
        private set

    init {
        loadFavoriteCharacters() // Cargar favoritos al inicializar el ViewModel
    }

    // Método para cargar personajes favoritos
    fun loadFavoriteCharacters() {
        viewModelScope.launch {
            isLoading.value = true
            loadError.value = "" // Limpiar errores anteriores
            try {
                // Obtener los personajes favoritos de la base de datos
                val characters = getFavouritesCharacterUseCase()
                // Mapear los resultados a CharacterListEntry
                favoriteCharacters.value = characters.map { characterEntity ->
                    CharacterListEntry(
                        characterName = characterEntity.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                        },
                        imageUrl = characterEntity.imageUrl,
                        id = characterEntity.id
                    )
                }
            } catch (e: Exception) {
                loadError.value = "Error al cargar personajes favoritos: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    // Método para recargar los personajes favoritos
    fun reloadFavoriteCharacters() {
        loadFavoriteCharacters() // Puedes reutilizar el mismo método para recargar
    }

}