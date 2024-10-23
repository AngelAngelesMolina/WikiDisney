package com.example.wikidisney.presentation.character_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.wikidisney.common.Resource
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.wikidisney.data.remote.dto.CharacterResponse
import com.example.wikidisney.presentation.character_detail.components.CharacterDetailStateWrapper
import com.example.wikidisney.presentation.character_detail.components.CharacterDetailTopSection
import timber.log.Timber


@Composable
fun CharacterDetailScreen(
    dominantColor: Color,
    navController: NavController,
    characterId: Int,
    topPadding: Dp = 20.dp,
    characterImageSize: Dp = 200.dp,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val characterInfo =
        produceState<Resource<CharacterResponse>>(initialValue = Resource.Loading()) {//take a initial
            // value
            value =
                viewModel.getPokemonInfo(characterId)// execute suspend function and asign the result
        }.value
    Timber.tag("CharacterDetailStateWrapper").w(characterInfo.toString())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        CharacterDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        CharacterDetailStateWrapper(
            characterInfo = characterInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + characterImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + characterImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(//Character image container
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if (characterInfo is Resource.Success) {
                characterInfo.data?.data?.let {
                    SubcomposeAsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = characterInfo.data.data.name,
                        modifier = Modifier
                            .size(characterImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }


    }

}