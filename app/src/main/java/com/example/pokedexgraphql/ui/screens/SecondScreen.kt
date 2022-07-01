package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun SecondScreen(navController: NavHostController){
    Box(modifier = Modifier.wrapContentSize()) {
        Text(text = "PAGE 2")
    }

}