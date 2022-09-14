package com.example.pokedexgraphql.ui.state

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import com.example.pokedexgraphql.viewmodel.PokedexViewModel

data class PokedexScreenState(
    var selectedIndex: Int = 0,
    val listState: LazyListState = LazyListState(0, 0),
    var pageIndex: MutableState<Int> = mutableStateOf(0),
    val noteOffsetValue: MutableState<Dp> = mutableStateOf(PokedexViewModel.NoteAnimationValue.START.step),
    val noteIsVisibleState:MutableState<Boolean> = mutableStateOf(false),
    val firstScreenState: MutableState<FirstScreenState> = mutableStateOf(FirstScreenState()),
    val secondScreenState: MutableState<SecondScreenState> = mutableStateOf(SecondScreenState()),
    val thirdScreenState: MutableState<ThirdScreenState> = mutableStateOf(ThirdScreenState()),
    val fourthScreenState: MutableState<FourthScreenState> = mutableStateOf(FourthScreenState()),
    )
