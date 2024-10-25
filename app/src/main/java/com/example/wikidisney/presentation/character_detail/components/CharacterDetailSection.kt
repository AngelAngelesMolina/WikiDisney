package com.example.wikidisney.presentation.character_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.presentation.character_detail.CharacterDetailViewModel
import timber.log.Timber
import java.util.Locale

@Composable
fun CharacterDetailSection(
    characterInfo: Resource<CharacterEntity>,
    viewModel: CharacterDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val character = characterInfo.data!!
    val scrollState = rememberScrollState()
    val characterTwo = viewModel.characterDetail.value
//    Timber.tag("character").w(characterInfo.data.isFavorite.toString())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {

        Text(
            text = "#${character.id} ${
                character.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
            }",
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.toggleFavorite() }) {
                Text(text = if (character.isFavorite) "Quitar de favoritos" else "Agregar a favoritos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            val filteredFilms = character.films?.filter { it.isNotBlank() } ?: emptyList()
            if (filteredFilms.isNotEmpty()) {
                CharacterDataDinamic(title = "Películas", itemsList = filteredFilms)
            }

            // Shows
            val filteredTvShows = character.tvShows?.filter { it.isNotBlank() } ?: emptyList()
            if (filteredTvShows.isNotEmpty()) {
                CharacterDataDinamic(title = "Shows", itemsList = filteredTvShows)
            }

            // Videojuegos
            val filteredVideoGames = character.videoGames?.filter { it.isNotBlank() } ?: emptyList()
            if (filteredVideoGames.isNotEmpty()) {
                CharacterDataDinamic(title = "Videojuegos", itemsList = filteredVideoGames)
            }
        }
    }
}