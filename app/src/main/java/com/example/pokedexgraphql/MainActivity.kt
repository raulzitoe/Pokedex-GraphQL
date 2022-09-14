package com.example.pokedexgraphql

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokedexgraphql.ui.screens.PokedexScreen
import com.example.pokedexgraphql.ui.state.PokedexScreenState
import com.example.pokedexgraphql.ui.theme.PokedexGraphQLTheme
import com.example.pokedexgraphql.viewmodel.PokedexViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokedexGraphQLTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: PokedexViewModel = hiltViewModel()
                    val context = LocalContext.current
                    PokedexScreen(
                        uiState = viewModel.pokedexScreenState,
                        onSpeakText = { viewModel.textToSpeech(context) },
                        onPageIndexChange = { viewModel.getPokemonByName() },
                        onClickDirectional = { direction -> viewModel.handleDirectionalClick(direction)}
                    )
                }
            }
        }
    }
}