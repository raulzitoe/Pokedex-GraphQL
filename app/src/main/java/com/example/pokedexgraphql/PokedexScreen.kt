package com.example.pokedexgraphql

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokedexScreen() {

    BoxWithConstraints(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.pokedex_bg),
            modifier = Modifier.fillMaxWidth(),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .offset(x = 0.dp, y = maxHeight * 0.2f)
                .padding(start = 30.dp, end = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.main_screen_bg),
                modifier = Modifier
                    .fillMaxWidth(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 0.dp, height = 220.dp)
                    .padding(start = 10.dp, end = 14.dp, top = 20.dp)
                    .align(Alignment.TopCenter)
                    .background(Color.Blue),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexPreview() {
    PokedexScreen()
}