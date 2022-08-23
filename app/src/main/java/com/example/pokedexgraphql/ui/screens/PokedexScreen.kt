package com.example.pokedexgraphql.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.pokedexgraphql.R
import com.example.pokedexgraphql.ui.navigation.Screen
import com.example.pokedexgraphql.ui.navigation.SetupNavGraph
import com.example.pokedexgraphql.viewmodel.PokedexViewModel
import com.example.pokedexgraphql.utils.OvalShape
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PokedexScreen(
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val navController = rememberAnimatedNavController()
    val infiniteTransition = rememberInfiniteTransition()
    val noteIsVisibleState = remember { mutableStateOf(false) }
    val speakTextState = remember { mutableStateOf(false) }
    val context = LocalContext.current

    if(speakTextState.value){
        viewModel.textToSpeech(context)
        speakTextState.value = false
    }

    val offsetNoteAnimation by animateDpAsState(
        targetValue = viewModel.noteOffsetValue.value,
        animationSpec = repeatable(
            iterations = 7,
            animation = tween(durationMillis = 200),
            repeatMode = RepeatMode.Reverse
        ),
        finishedListener = { noteIsVisibleState.value = false }
    )

    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        )
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        DrawBackground()
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(top = 30.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    LargeCamera()
                    Led(modifier = Modifier.size(30.dp), animatedColor = animatedColor)
                    Led(modifier = Modifier.size(30.dp), animatedColor = animatedColor)
                    Led(modifier = Modifier.size(30.dp), animatedColor = animatedColor)
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 0.dp)
            ) {
                DrawMiniScreen(navController = navController, viewModel)
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                DrawSpeaker(
                    offsetNoteAnimation = offsetNoteAnimation,
                    noteIsVisible = noteIsVisibleState.value
                )
                StartButton(
                    navController = navController,
                    modifier = Modifier.padding(10.dp),
                    viewModel = viewModel
                )
                DirectionalButtons(
                    navController = navController,
                    viewModel,
                    noteIsVisibleState = noteIsVisibleState,
                    speakTextState = speakTextState
                )
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
    viewModel: PokedexViewModel
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
fun DirectionalButtons(
    navController: NavHostController,
    viewModel: PokedexViewModel,
    noteIsVisibleState: MutableState<Boolean>,
    speakTextState: MutableState<Boolean>
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Button(
            onClick = {
                if (viewModel.selectedIndex.value == 0 || viewModel.pageIndex.value != 1) return@Button
                viewModel.selectedIndex.value -= 1
                noteIsVisibleState.value = true
                if (viewModel.noteOffsetValue.value == PokedexViewModel.NoteAnimationValue.START.step) {
                    viewModel.noteOffsetValue.value =
                        PokedexViewModel.NoteAnimationValue.FINISH.step
                } else {
                    viewModel.noteOffsetValue.value =
                        PokedexViewModel.NoteAnimationValue.START.step
                }
                speakTextState.value = true
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
                        3 -> navController.navigate(Screen.Fourth.route)
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
                if ((viewModel.selectedIndex.value == viewModel.pokemons.value.size - 1) || viewModel.pageIndex.value != 1) return@Button
                viewModel.selectedIndex.value += 1
                noteIsVisibleState.value = true
                if (viewModel.noteOffsetValue.value == PokedexViewModel.NoteAnimationValue.START.step) {
                    viewModel.noteOffsetValue.value =
                        PokedexViewModel.NoteAnimationValue.FINISH.step
                } else {
                    viewModel.noteOffsetValue.value =
                        PokedexViewModel.NoteAnimationValue.START.step
                }
                speakTextState.value = true
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

@Composable
fun StartButton(
    navController: NavHostController,
    viewModel: PokedexViewModel,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier, color = Color.Transparent) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Start")
            Button(
                onClick = {
                    if (viewModel.pageIndex.value != 1) {
                        navController.navigate(Screen.Home.route)
                    }
                },
                modifier = Modifier.size(height = 10.dp, width = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
            }
        }
    }
}

@Composable
fun DrawSpeaker(modifier: Modifier = Modifier, offsetNoteAnimation: Dp, noteIsVisible: Boolean) {
    Box() {
        Image(
            painter = painterResource(id = R.drawable.ic_speaker_grill),
            modifier = Modifier.size(width = 100.dp, height = 100.dp),
            contentDescription = "",
        )
        Image(
            painter = painterResource(R.drawable.ic_music_note),
            contentDescription = null,
            modifier = Modifier
                .height(90.dp)
                .absoluteOffset(x = offsetNoteAnimation)
                .alpha(if (noteIsVisible) 1f else 0f)
        )
    }

}

@Composable
fun Led(modifier: Modifier = Modifier, animatedColor: Color = Color.Yellow) {
    Box(modifier = modifier.size(100.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = CircleShape)
                .background(Color(0xFF001460))
        ) {

        }
        Box(
            modifier = Modifier
                .fillMaxSize(0.75f)
                .clip(shape = CircleShape)
                .background(animatedColor)
        )
    }
}

@Composable
fun LargeCamera(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier.size(100.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(x = 5.dp, y = 2.dp)
                .clip(shape = CircleShape)
                .background(Color(0xFF70225C))
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    10
                        .dp, CircleShape, ambientColor = Color.Black, spotColor = Color.Black
                )
                .clip(shape = CircleShape)
                .background(Color.White)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(0.75f)
                .clip(shape = CircleShape)
                .background(Color(0xFF001460))
        )
        Box(
            modifier = Modifier
                .fillMaxSize(0.65f)
                .clip(shape = CircleShape)
                .background(Color(0xFF01DBFF))
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.17f)
                .fillMaxHeight(0.30f)
                .offset(x = (-14).dp, y = (-14).dp)
                .rotate(45f)
                .clip(OvalShape(2.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.85f)
                    .clip(OvalShape(2.dp))
                    .background(Color(0xFF70E0E1))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LargeCamera()
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