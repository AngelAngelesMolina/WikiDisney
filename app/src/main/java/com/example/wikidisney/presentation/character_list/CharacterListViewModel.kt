package com.example.wikidisney.presentation.character_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.wikidisney.common.Resource
import com.example.wikidisney.domain.model.CharacterListEntry
import com.example.wikidisney.domain.use_case.get_characters.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private var curPage = 1

    var characterList = mutableStateOf<List<CharacterListEntry>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var endReached = mutableStateOf(false)

    private var cachedCharacterList = listOf<CharacterListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)
    
    init {
        loadCharacterPaginated()
    }

    fun loadCharacterPaginated() {
        viewModelScope.launch {
            isLoading.value = true
            val result = getCharactersUseCase(curPage)
            when (result) {
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    result.data?.let { characters ->
                        val characterEntries = characters.map { characterEntity ->
                            CharacterListEntry(
                                characterName = characterEntity.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                                },
                                imageUrl = characterEntity.imageUrl, // Aquí se usa la URL de la imagen correctamente
                                id = characterEntity.id
                            )
                        }
                        // Solo agregar nuevos personajes que no están en la lista actual
                        val newCharacters = characterEntries.filter { newEntry ->
                            !characterList.value.any { it.id == newEntry.id } // Asegúrate de que no esté ya en la lista
                        }
                        // Actualiza la lista de personajes solo si hay nuevos
                        if (newCharacters.isNotEmpty()) {
                            characterList.value += newCharacters // Solo agrega los nuevos personajes
                        }
                        curPage++
                        loadError.value = ""
                    } ?: run {
                        loadError.value = "No se pudieron cargar los personajes"
                    }
                    isLoading.value = false
                }
            }
        }
    }
    fun calcDominantcolor(drawable: Drawable, onFinished: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinished(Color(colorValue))
            }
        }
    }
    
    fun searchCharacterList(query: String) {
        val listToSearch = if (isSearchStarting) { //our search query es empty
            characterList.value
        } else {
            cachedCharacterList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                characterList.value = cachedCharacterList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.characterName.contains(
                    query.trim(),
                    ignoreCase = true
                )|| it.id.toString() == query.trim()
            }
            if (isSearchStarting) {
                cachedCharacterList = characterList.value
                isSearchStarting = false
            }
            characterList.value = results
            isSearching.value = true
        }
    }

}