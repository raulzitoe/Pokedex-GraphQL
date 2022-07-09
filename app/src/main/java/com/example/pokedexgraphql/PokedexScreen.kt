package com.example.pokedexgraphql

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.pokedexgraphql.ui.navigation.Screen
import com.example.pokedexgraphql.ui.navigation.SetupNavGraph

@Composable
fun PokedexScreen() {
    val navController = rememberNavController()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrawBackground()
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            Box(modifier = Modifier.fillMaxWidth().height(100.dp).padding(top = 30.dp)){

            }
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 40.dp)
            ) {
                DrawMiniScreen(navController = navController)
            }
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 50.dp, top = 10.dp)
            ) {
                DirectionalButtons(navController = navController)
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
fun DrawMiniScreen(navController: NavHostController) {
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
            SetupNavGraph(navController = navController)
        }

    }
}

@Composable
fun DirectionalButtons(navController: NavHostController) {
    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Button(
            onClick = { navController.navigate(Screen.Second.route) },
            content = { Icon(Icons.Filled.PlayArrow, "", modifier = Modifier
                .rotate(270f)
                .fillMaxSize(0.8f)) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(35.dp)
                .height(35.dp),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
            contentPadding = PaddingValues(start = 0.dp)
        )
        Row {
            Button(
                onClick = { navController.navigate(Screen.Second.route) },
                content = { Icon(Icons.Filled.PlayArrow, "", modifier = Modifier
                    .rotate(180f)
                    .fillMaxSize(0.8f)) },
                shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                contentPadding = PaddingValues(start = 0.dp)
            )
            Button(
                onClick = {  },
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),
                content = {}
            )
            Button(
                onClick = { navController.navigate(Screen.Second.route) },
                content = { Icon(Icons.Filled.PlayArrow, "", modifier = Modifier.fillMaxSize(0.8f)) },
                shape = RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp),
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp),

                contentPadding = PaddingValues(start = 0.dp)
            )
        }
        Button(
            onClick = { navController.navigate(Screen.Second.route) },
            content = { Icon(Icons.Filled.PlayArrow, "", modifier = Modifier
                .fillMaxSize(0.8f)
                .rotate(90f)) },
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

@Preview
@Composable
fun DirectionalButtonsPreview() {
    DirectionalButtons(navController = rememberNavController())
}