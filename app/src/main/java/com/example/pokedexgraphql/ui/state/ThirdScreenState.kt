package com.example.pokedexgraphql.ui.state

data class ThirdScreenState(
    val name: String = "",
    val number: String = "",
    val types: List<String> = emptyList(),
    val maxHP: Int = 0,
    val maxCP: Int = 0,
    val minWeight: String = "",
    val maxWeight: String = "",
    val minHeight: String = "",
    val maxHeight: String = "",
    )