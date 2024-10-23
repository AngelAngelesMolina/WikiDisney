package com.example.wikidisney.presentation.character_list

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
                    val characterEntries =
                        result.data!!.data.mapIndexed { index, characterInfoEntry ->
                            CharacterListEntry(
                                characterInfoEntry.name.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(
                                        Locale.ROOT
                                    ) else it.toString()
                                },
                                characterInfoEntry.imageUrl,
                                characterInfoEntry._id
                            )

                        }
                    curPage++
                    loadError.value = ""
                    isLoading.value = false
                    characterList.value += characterEntries
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