package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedexgraphql.R
import com.example.pokedexgraphql.ui.state.SecondScreenState

@Composable
fun SecondScreen(
    uiState: SecondScreenState,
    pageIndex: Int,
    onPageIndexChange: () -> Unit
) {
    LaunchedEffect(pageIndex){
        if(pageIndex == 1){
            onPageIndexChange()
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 5.dp), color = Color.Transparent) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = uiState.name,
                modifier = Modifier.padding(top = 5.dp)
            )

            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = uiState.classification,
                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                fontSize = 10.sp
            )

            Card {
                AsyncImage(
                    model = uiState.image ?: R.drawable.ic_downloading,
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp),
                    placeholder = painterResource(R.drawable.ic_downloading)
                )
            }
        }
    }
}
