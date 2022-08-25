package com.example.pokedexgraphql.ui.state

import com.example.pokedexgraphql.graphql.PokemonByNameQuery

data class FourthScreenState(
   val resistant: List<String> = emptyList(),
   val weaknesses: List<String> = emptyList(),
   val attacks: String = "",
   val evolutions: List<PokemonByNameQuery.Evolution> = emptyList()
)
