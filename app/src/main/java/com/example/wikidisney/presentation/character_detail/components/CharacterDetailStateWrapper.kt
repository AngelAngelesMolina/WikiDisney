package com.example.wikidisney.presentation.character_detail.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wikidisney.common.Resource
import com.example.wikidisney.data.database.entities.CharacterEntity
import com.example.wikidisney.data.remote.dto.CharacterResponse
import timber.log.Timber

@Composable
fun CharacterDetailStateWrapper(
    characterInfo: Resource<CharacterEntity>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when (characterInfo) {
        is Resource.Success -> {
            CharacterDetailSection(
                characterInfo = characterInfo,
                modifier = modifier.offset(y = (-20).dp)
            )
        }

        is Resource.Error -> {
            Text(
                text = characterInfo.message!!, color = Color.Red,
                modifier = modifier
            )

        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }
    }
}