package com.example.pokedexgraphql.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pokedexgraphql.ui.state.ThirdScreenState

@Composable
fun ThirdScreen(
    uiState: ThirdScreenState
) {
    Surface(color = Color.Transparent) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Name: " + uiState.name,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Number: " + uiState.number,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Types: " + uiState.types.joinToString(),
                modifier = Modifier.padding(top = 5.dp)
            )
            Row {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "Max HP: " + uiState.maxHP,
                    modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = "Max CP: " + uiState.maxCP,
                    modifier = Modifier.padding(top = 5.dp, start = 20.dp)
                )
            }
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Weight: " + uiState.minWeight
                        + " to " + uiState.maxWeight,
                modifier = Modifier.padding(top = 5.dp)
            )
            Text(
                color = Color.White,
                textAlign = TextAlign.Center,
                text = "Height: " + uiState.minHeight
                        + " to " + uiState.maxHeight,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x3282F6)
@Composable
fun ThirdScreenPreview() {
    ThirdScreen(uiState = ThirdScreenState())
}