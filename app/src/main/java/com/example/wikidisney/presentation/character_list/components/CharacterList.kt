package com.example.wikidisney.presentation.character_list.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.wikidisney.presentation.character_list.CharacterListViewModel
import timber.log.Timber

@Composable
fun CharacterList(
    navController: NavController,
    viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characterList by remember { viewModel.characterList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
    val isSearching by remember { viewModel.isSearching }


    LazyColumn(contentPadding = PaddingValues(16.dp)) {

        val itemCount = if (characterList.size % 2 == 0) {
            characterList.size / 2
        } else {
            characterList.size / 2 + 1
        }

        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) { //we scroll to the bottom
                viewModel.loadCharacterPaginated()
            }
            CharacterRow(rowIndex = it, entries = characterList, navController = navController)
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
        if (loadError.isNotEmpty()) {
            //TODO: here we're gonna use our database info
            /*RetrySection(loadError) {
                viewModel.()
            }*/
        }
    }
}