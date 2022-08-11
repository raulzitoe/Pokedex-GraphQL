package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel

@Composable
fun SecondScreen(
    navController: NavHostController, viewModel: HomeScreenViewModel, pokeName: String
) {
    viewModel.getPokemonByName(pokeName)
    Box(modifier = Modifier.wrapContentSize()) {
        Column {
            Text(text = "PAGE 2")
            Text(text = viewModel.pokemon.value.name ?: "")
            Text(text = viewModel.pokemon.value.weight?.minimum ?: "")
        }
    }
}