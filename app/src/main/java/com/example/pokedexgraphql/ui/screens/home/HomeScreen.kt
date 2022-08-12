package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.pokedexgraphql.graphql.PokemonDBQuery
import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController, viewModel: HomeScreenViewModel
) {
    viewModel.getPokemons()
    viewModel.pageIndex.value = 1

    val listState = remember { viewModel.listState }
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = remember { mutableStateOf(0) }

    if (selectedIndex.value != viewModel.selectedIndex.value) {
        selectedIndex.value = viewModel.selectedIndex.value
        animate(coroutineScope, listState, selectedIndex.value)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(viewModel.pokemons.value) { index, pokemon ->
            PokemonItem(
                index = index,
                selected = viewModel.selectedIndex.value == index,
                viewModel = viewModel,
                pokemon = pokemon
            )
        }

    }

}

fun animate(
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    index: Int
) {
    coroutineScope.launch {
        listState.animateScrollToItem(index = index)
    }
}

@Composable
fun PokemonItem(
    index: Int,
    selected: Boolean,
    viewModel: HomeScreenViewModel,
    pokemon: PokemonDBQuery.Pokemon
) {
    Text(
        text = pokemon.number + " - " + pokemon.name,
        modifier = Modifier
            .clickable {
                viewModel.selectedIndex.value = index
            }
            .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(12.dp)
    )
}