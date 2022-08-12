package com.example.pokedexgraphql.ui.screens.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexgraphql.GraphQLManager
import com.example.pokedexgraphql.graphql.PokemonByNameQuery
import com.example.pokedexgraphql.graphql.PokemonDBQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    val pokemons: MutableState<List<PokemonDBQuery.Pokemon>> = mutableStateOf(emptyList())
    var selectedIndex = mutableStateOf(0)
    var pageIndex = mutableStateOf(1)
    val listState = LazyListState(0, 0)
    val pokemon: MutableState<PokemonByNameQuery.Pokemon> = mutableStateOf(PokemonByNameQuery.Pokemon(
        name = null, weight = null, height = null, image = null))

    fun getPokemons() {
        viewModelScope.launch {
            GraphQLManager.getPokemons().data?.pokemons?.mapNotNull { it }?.let {
                pokemons.value = it
            }
        }
    }

    fun getPokemonByName(name: String) {

        viewModelScope.launch {
            GraphQLManager.getPokemonByName(name).data?.pokemon?.let {
                pokemon.value = it
            }
        }
    }

    fun animateScroll (){
        viewModelScope.launch {
            listState.animateScrollToItem(selectedIndex.value)
        }
    }

    fun clearPokemon() {
        pokemon.value = PokemonByNameQuery.Pokemon(
            name = null, weight = null, height = null, image = null)
    }
}