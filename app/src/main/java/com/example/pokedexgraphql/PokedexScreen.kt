package com.example.pokedexgraphql

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexgraphql.ui.navigation.Screen
import com.example.pokedexgraphql.ui.navigation.SetupNavGraph
import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokedexScreen(
    viewModel: HomeScreenViewModel
) {
    val navController = rememberAnimatedNavController()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrawBackground()
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 30.dp)
            ) {

            }
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 40.dp)
            ) {
                DrawMiniScreen(navController = navController, viewModel)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 50.dp, top = 10.dp)
            ) {
                DirectionalButtons(navController = navController, viewModel)
            }
        }
    }
}

@Composable
fun DrawBackground() {
    Image(
        painter = painterResource(id = R.drawable.pokedex_bg),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun DrawMiniScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_screen_bg),
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = "",
            contentScale = ContentScale.FillBounds

        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 14.dp, bottom = 60.dp, top = 20.dp)
                .background(Color.Blue),
        ) {
            SetupNavGraph(navController = navController, viewModel)
        }

    }
}

@Composable
fun DirectionalButtons(navController: NavHostController, viewModel: HomeScreenViewModel) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Button(
            onClick = {
                if (viewModel.selectedIndex.value == 0) return@Button
                viewModel.selectedIndex.value -= 1
            },
            content = {
                Icon(
                    Icons.Filled.PlayArrow, "", modifier = Modifier
                        .rotate(270f)
                        .fillMaxSize(0.8f)
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(35.dp)
                .height(35.dp),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            contentPadding = PaddingValues(start = 0.dp)
        )
        Row {
            Button(
                onClick = {
                    if (viewModel.selectedIndex.value == 2) {
                        viewModel.clearPokemon()
                    }
                    navController.navigateUp()
                },
                content = {
                    Icon(
                        Icons.Filled.PlayArrow, "", modifier = Modifier
                            .rotate(180f)
                            .fillMaxSize(0.8f)
                    )
                },
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                contentPadding = PaddingValues(start = 0.dp)
            )
            Button(
                onClick = { },
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                content = {}
            )
            Button(
                onClick = {
                    when (viewModel.pageIndex.value) {
                        1 -> navController.navigate("${Screen.Second.route}?pokeName=${viewModel.pokemons.value[viewModel.selectedIndex.value].name}")
                        2 -> navController.navigate(Screen.Third.route)
                    }


                },
                content = {
                    Icon(
                        Icons.Filled.PlayArrow,
                        "",
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                },
                shape = RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),

                contentPadding = PaddingValues(start = 0.dp)
            )
        }
        Button(
            onClick = {
                if (viewModel.selectedIndex.value == viewModel.pokemons.value.size - 1) return@Button
                viewModel.selectedIndex.value += 1
            },
            content = {
                Icon(
                    Icons.Filled.PlayArrow, "", modifier = Modifier
                        .fillMaxSize(0.8f)
                        .rotate(90f)
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(35.dp)
                .height(35.dp),
            shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp),
            contentPadding = PaddingValues(start = 0.dp),
        )
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PokedexPreview() {
//    PokedexScreen()
//}
//
//@Preview
//@Composable
//fun MiniScreenPreview(){
//    DrawMiniScreen(navController = rememberNavController())
//}

//@Preview
//@Composable
//fun DirectionalButtonsPreview() {
//    DirectionalButtons(navController = rememberNavController())
//}