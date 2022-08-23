package com.example.pokedexgraphql.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.pokedexgraphql.ui.screens.FourthScreen
import com.example.pokedexgraphql.ui.screens.FirstScreen
import com.example.pokedexgraphql.ui.screens.SecondScreen
import com.example.pokedexgraphql.ui.screens.ThirdScreen
import com.example.pokedexgraphql.viewmodel.PokedexViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(navController: NavHostController, viewModel: PokedexViewModel) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            exitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            popEnterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))},
            popExitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))}
        ) {
            viewModel.pageIndex.value = 1
            FirstScreen(viewModel)
        }
        composable(
            route = "${Screen.Second.route}?pokeName={pokeName}",
            arguments = listOf(navArgument("pokeName") { defaultValue = "" }),
            enterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            exitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            popEnterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))},
            popExitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))}
        ) {
            SecondScreen(viewModel, pokeName = it.arguments?.getString("pokeName","") ?: "")
        }
        composable(
            route = Screen.Third.route,
            enterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            exitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            popEnterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))},
            popExitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))}
        ) {
            ThirdScreen(viewModel)
        }
        composable(
            route = Screen.Fourth.route,
            enterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            exitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(700))},
            popEnterTransition = {slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))},
            popExitTransition = {slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700))}
        ) {
            FourthScreen(navController, viewModel)
        }
    }
}