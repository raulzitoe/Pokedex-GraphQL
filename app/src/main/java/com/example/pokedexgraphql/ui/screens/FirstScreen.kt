package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedexgraphql.graphql.PokemonDBQuery
import com.example.pokedexgraphql.ui.state.FirstScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FirstScreen(
    listIndex: Int,
    listState: LazyListState,
    onIndexChange: (Int) -> Unit,
    uiState: FirstScreenState
) {
    val coroutineScope = rememberCoroutineScope()
    val selectedIndex = remember { mutableStateOf(0) }

    if (selectedIndex.value != listIndex) {
        selectedIndex.value = listIndex
        LaunchedEffect(Unit) {
            animate(coroutineScope, listState, selectedIndex.value)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        itemsIndexed(uiState.pokemons) { index, pokemon ->
            PokemonItem(
                index = index,
                selected = listIndex == index,
                pokemon = pokemon,
                onClickItem = { itemIndex -> onIndexChange(itemIndex) }
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
    onClickItem: (Int) -> Unit,
    pokemon: PokemonDBQuery.Pokemon
) {
    Text(
        text = pokemon.number + " - " + pokemon.name,
        modifier = Modifier
            .clickable {
                onClickItem(index)
            }
            .background(if (selected) MaterialTheme.colors.secondary else Color.Transparent)
            .fillMaxWidth()
            .padding(12.dp)
    )
}