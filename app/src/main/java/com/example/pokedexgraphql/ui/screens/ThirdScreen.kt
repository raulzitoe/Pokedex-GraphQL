package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokedexgraphql.ui.screens.home.HomeScreenViewModel

@Composable
fun ThirdScreen(
    viewModel: HomeScreenViewModel
) {
    viewModel.pageIndex.value = 3

    Surface(color = Color.Transparent) {
        Column (modifier = Modifier.fillMaxSize()){
            Text(text = "THIRD SCREEN")
        }
    }

}