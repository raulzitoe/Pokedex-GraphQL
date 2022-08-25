package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.pokedexgraphql.R
import com.example.pokedexgraphql.viewmodel.PokedexViewModel

@Composable
fun SecondScreen(
    viewModel: PokedexViewModel, pokeName: String
) {
    LaunchedEffect(viewModel.pageIndex.value){
        if(viewModel.pageIndex.value == 1){
            viewModel.getPokemonByName()
        }
    }

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

            Card {
                AsyncImage(
                    model = viewModel.pokemon.value.image ?: R.drawable.ic_downloading,
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp),
                    placeholder = painterResource(R.drawable.ic_downloading)
                )
            }
        }
    }
}
