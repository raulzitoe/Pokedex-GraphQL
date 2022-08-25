package com.example.pokedexgraphql.ui.state

import com.example.pokedexgraphql.graphql.PokemonDBQuery

data class FirstScreenState(
    val pokemons: List<PokemonDBQuery.Pokemon> = emptyList()
)
