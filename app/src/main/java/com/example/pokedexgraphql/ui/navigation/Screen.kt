package com.example.pokedexgraphql.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen(route = "home_screen")
    object Second: Screen(route = "second_screen")
    object Third: Screen(route = "third_screen")
    object Fourth: Screen(route = "fourth_screen")
}