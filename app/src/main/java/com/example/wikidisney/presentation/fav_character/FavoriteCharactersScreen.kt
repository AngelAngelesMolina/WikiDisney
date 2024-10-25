package com.example.wikidisney.presentation.fav_character

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wikidisney.presentation.character_detail.components.CharacterDetailTopSection
import com.example.wikidisney.presentation.character_list.components.CharacterRow

@Composable
fun FavoriteCharactersScreen(
    navController: NavController,
    viewModel: FavCharacterViewModel = hiltViewModel()
) {
    val favoriteCharacterList by remember { viewModel.favoriteCharacters }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }
    /*val favoriteCharacterList by remember { viewModel.favoriteCharacters }
    val isLoading by remember { viewModel.isLoading }
    val loadError by remember { viewModel.loadError }

    // Usar LaunchedEffect para recargar los datos
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    LaunchedEffect(navBackStackEntry.value) {
//        viewModel.reloadFavoriteCharacters() // Asegúrate de tener un método para recargar
    }*/
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        ) {
            CharacterDetailTopSection(
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Mis favoritos",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            val itemCount = if (favoriteCharacterList.size % 2 == 0) {
                favoriteCharacterList.size / 2
            } else {
                favoriteCharacterList.size / 2 + 1
            }

            items(itemCount) { index ->
                CharacterRow(
                    rowIndex = index,
                    entries = favoriteCharacterList,
                    navController = navController,
                    isClickable = false
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            if (favoriteCharacterList.isEmpty() || loadError.isNotEmpty()) {
                Text(text = "No cuenta con personajes.")
            }
        }
    }
}
