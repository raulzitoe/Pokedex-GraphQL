package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = viewModel()
) {
    viewModel.getPokemons()
    LazyColumn() {
        items(viewModel.pokemons.value) { pokemon ->
            Text(text = pokemon.name!! )
        }
    }
}