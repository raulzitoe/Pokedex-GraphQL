package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokedexgraphql.viewmodel.PokedexViewModel

@Composable
fun ThirdScreen(
    viewModel: PokedexViewModel
) {
    viewModel.pageIndex.value = 3

    Surface(color = Color.Transparent) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Name: " + viewModel.pokemon.value.name,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Number: " + viewModel.pokemon.value.number,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Types: " + viewModel.pokemon.value.types?.joinToString(),
                modifier = Modifier.padding(top = 5.dp)
            )
            Row {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "Max HP: " + viewModel.pokemon.value.maxHP,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "Max CP: " + viewModel.pokemon.value.maxCP,
                    modifier = Modifier.padding(top = 5.dp, start = 20.dp)
                )
            }
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Weight: " + viewModel.pokemon.value.weight?.minimum
                        + " to " + viewModel.pokemon.value.weight?.maximum,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Height: " + viewModel.pokemon.value.height?.minimum
                        + " to " + viewModel.pokemon.value.height?.maximum,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}