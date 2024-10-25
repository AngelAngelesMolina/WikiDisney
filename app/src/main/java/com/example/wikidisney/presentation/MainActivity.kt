package com.example.wikidisney.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wikidisney.presentation.character_detail.CharacterDetailScreen
import com.example.wikidisney.presentation.character_list.CharacterListScreen
import com.example.wikidisney.presentation.fav_character.FavoriteCharactersScreen
import com.example.wikidisney.presentation.theme.WikiDisneyTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WikiDisneyTheme {
                // A surface container using the 'background' color from the theme
                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }*/
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.CharacterListScreen.route
                ) {
                    //Defining our screen
                    composable(Screen.CharacterListScreen.route) {
                        CharacterListScreen(navController)
                    }
                    //putting arguments into our navigation
                    composable(Screen.CharacterDetailScreen.route + "/{dominantColor}/{characterId}",
                        arguments = listOf(
                            navArgument("dominantColor") {
                                type = NavType.IntType //type of argument
                            },
                            navArgument("characterId") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let {
                                Color(it)
                            } ?: Color.White
                        }
                        val characterId = remember {
                            it.arguments?.getInt("characterId")
                        }
                        if (characterId != null) {
                            CharacterDetailScreen(
                                dominantColor = dominantColor,
                                characterId = characterId,
                                navController = navController
                            )
                        }
                    }
                    composable(Screen.FavoriteCharactersScreen.route) {
                        FavoriteCharactersScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WikiDisneyTheme {
        Greeting("Android")
    }
}