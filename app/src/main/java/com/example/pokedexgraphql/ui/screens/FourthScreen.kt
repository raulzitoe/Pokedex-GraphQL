package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedexgraphql.ui.navigation.Screen
import com.example.pokedexgraphql.viewmodel.PokedexViewModel

@Composable
fun FourthScreen(
    navController: NavController, viewModel: PokedexViewModel
) {
    viewModel.pageIndex.value = 4
    Surface(color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Text(
                color = Color.White,
                textAlign = TextAlign.Justify,
                text = "Resistant: " + viewModel.pokemon.value.resistant?.joinToString(),
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Justify,
                text = "Weaknesses: " + viewModel.pokemon.value.weaknesses?.joinToString(),
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Justify,
                text = "Attacks: " + viewModel.getPokemonAttacks(),
                modifier = Modifier.padding(top = 5.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    text = "Evolutions: ",
                    modifier = Modifier.padding(top = 5.dp)
                )
                viewModel.pokemon.value.evolutions?.map { item -> item?.name }?.forEach {
                    Button(
                        onClick = { navController.navigate("${Screen.Second.route}?pokeName=${it}") },
                        modifier = Modifier.padding(end = 3.dp).height(30.dp),
                        contentPadding = PaddingValues(start = 5.dp, end = 5.dp, top = 2.dp, bottom = 2.dp)
                    ) {
                        Text(text = it ?: "")
                    }
                }
            }

        }
    }
}