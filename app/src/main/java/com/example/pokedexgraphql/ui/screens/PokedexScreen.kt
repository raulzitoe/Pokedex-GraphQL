package com.example.pokedexgraphql.ui.screens

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
import com.example.pokedexgraphql.R
import com.example.pokedexgraphql.ui.state.FirstScreenState
import com.example.pokedexgraphql.ui.state.FourthScreenState
import com.example.pokedexgraphql.ui.state.SecondScreenState
import com.example.pokedexgraphql.ui.state.ThirdScreenState
import com.example.pokedexgraphql.utils.Direction
import com.example.pokedexgraphql.viewmodel.PokedexViewModel
import com.example.pokedexgraphql.utils.OvalShape
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun PokedexScreen(
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val infiniteTransition = rememberInfiniteTransition()
    val speakTextState = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val firstScreenState = viewModel.firstScreenState
    val secondScreenState = viewModel.secondScreenState
    val thirdScreenState = viewModel.thirdScreenState
    val fourthScreenState = viewModel.fourthScreenState

    if (speakTextState.value) {
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
        finishedListener = { viewModel.noteIsVisibleState.value = false }
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
                DrawMiniScreen(
                    firstScreenState = firstScreenState,
                    secondScreenState = secondScreenState,
                    thirdScreenState = thirdScreenState,
                    fourthScreenState = fourthScreenState,
                    viewModel = viewModel,
                    pageIndex = viewModel.pageIndex.value
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                DrawSpeaker(
                    offsetNoteAnimation = offsetNoteAnimation,
                    noteIsVisible = viewModel.noteIsVisibleState.value
                )
                StartButton(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        viewModel.pageIndex.value = 0
                    }
                )
                DirectionalButtons(
                    speakTextState = speakTextState,
                    onClick = { direction -> viewModel.handleDirectionalClick(direction)  }
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DrawMiniScreen(
    viewModel: PokedexViewModel,
    pageIndex: Int,
    firstScreenState: FirstScreenState,
    secondScreenState: SecondScreenState,
    thirdScreenState: ThirdScreenState,
    fourthScreenState: FourthScreenState
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    LaunchedEffect(pageIndex) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(pageIndex)
        }
    }

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
            HorizontalPager(count = 4, state = pagerState) { page ->
                when (page) {
                    0 -> FirstScreen(
                        listIndex = viewModel.selectedIndex.value,
                        listState = viewModel.listState,
                        onIndexChange = { index -> viewModel.selectedIndex.value = index },
                        uiState = firstScreenState
                    )
                    1 -> SecondScreen(
                        uiState = secondScreenState,
                        onPageIndexChange = { viewModel.getPokemonByName() },
                        pageIndex = viewModel.pageIndex.value
                    )
                    2 -> ThirdScreen(uiState = thirdScreenState)
                    3 -> FourthScreen(uiState = fourthScreenState)
                }
            }
        }
    }
}

@Composable
fun DirectionalButtons(
    speakTextState: MutableState<Boolean>,
    onClick: (Direction) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Button(
            onClick = {
                onClick(Direction.UP)
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
                    onClick(Direction.LEFT)
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
                    onClick(Direction.RIGHT)
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
                onClick(Direction.DOWN)
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(modifier = modifier, color = Color.Transparent) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Start")
            Button(
                onClick = {
                    onClick()
                },
                modifier = Modifier.size(height = 10.dp, width = 40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
            }
        }
    }
}

@Composable
fun DrawSpeaker(offsetNoteAnimation: Dp, noteIsVisible: Boolean) {
    Box {
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