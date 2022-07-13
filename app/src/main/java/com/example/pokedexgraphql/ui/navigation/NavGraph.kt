package com.example.pokedexgraphql.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokedexgraphql.ui.screens.HomeScreen
import com.example.pokedexgraphql.ui.screens.SecondScreen
import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel

@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: HomeScreenViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController, viewModel)
        }
        composable(
            route = Screen.Second.route
        ) {
            SecondScreen(navController, viewModel)
        }
    }
}