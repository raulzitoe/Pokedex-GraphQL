package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel

@Composable
fun SecondScreen(
    navController: NavHostController, viewModel: HomeScreenViewModel, pokeName: String
) {
    viewModel.getPokemonByName(pokeName)
    viewModel.pageIndex.value = 2

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 5.dp), color = Color.Transparent) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = viewModel.pokemon.value.name ?: "",
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = viewModel.pokemon.value.classification ?: "",
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                fontSize = 10.sp
            )

            Card() {
                AsyncImage(
                    model = viewModel.pokemon.value.image,
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
