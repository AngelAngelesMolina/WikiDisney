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
import com.example.wikidisney.data.remote.dto.CharacterInfo
import java.util.Locale

@Composable
fun CharacterDetailSection(
    characterInfo: CharacterInfo,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 100.dp)
            .verticalScroll(scrollState)
    ) {

        Text(
            text = "#${characterInfo._id} ${
                characterInfo.name.replaceFirstChar {
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
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Agregar a favoritos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            characterInfo.films.takeIf { it.isNotEmpty() }?.let { filmsList ->
                CharacterDataDinamic(title = "Películas",itemsList = filmsList)
            }
            characterInfo.tvShows.takeIf { it.isNotEmpty() }?.let { tvShowsList ->
                CharacterDataDinamic(title = "Shows",itemsList = tvShowsList)
            }
            characterInfo.videoGames.takeIf { it.isNotEmpty() }?.let { videoGamesList ->
                CharacterDataDinamic(title = "Videojuegos",itemsList = videoGamesList)
            }
        }
    }
}