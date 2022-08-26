package com.example.pokedexgraphql.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexgraphql.GraphQLManager
import com.example.pokedexgraphql.graphql.PokemonByNameQuery
import com.example.pokedexgraphql.ui.state.FirstScreenState
import com.example.pokedexgraphql.ui.state.FourthScreenState
import com.example.pokedexgraphql.ui.state.SecondScreenState
import com.example.pokedexgraphql.ui.state.ThirdScreenState
import com.example.pokedexgraphql.utils.Constants
import com.example.pokedexgraphql.utils.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor() : ViewModel() {
    val selectedIndex = mutableStateOf(0)
    val pageIndex = mutableStateOf(0)
    val listState = LazyListState(0, 0)
    val noteOffsetValue = mutableStateOf(NoteAnimationValue.START.step)
    private var textToSpeech: TextToSpeech? = null
    var firstScreenState by mutableStateOf(FirstScreenState())
        private set
    var secondScreenState by mutableStateOf(SecondScreenState())
        private set
    var thirdScreenState by mutableStateOf(ThirdScreenState())
        private set
    var fourthScreenState by mutableStateOf(FourthScreenState())
        private set
    val noteIsVisibleState = mutableStateOf(false)

    init {
        getPokemons()
    }

    enum class NoteAnimationValue(val step: Dp) {
        START(2.dp), FINISH(20.dp)
    }

    private fun getPokemons() {
        viewModelScope.launch {
            GraphQLManager.getPokemons().data?.pokemons?.mapNotNull { it }?.let {
                firstScreenState = FirstScreenState(it)
            }
        }
    }

    fun getPokemonByName() {
        val name = firstScreenState.pokemons[selectedIndex.value].name ?: ""
        viewModelScope.launch {
            GraphQLManager.getPokemonByName(name).data?.pokemon?.let {
                secondScreenState = SecondScreenState(
                    name = it.name ?: "",
                    classification = it.classification ?: "",
                    image = it.image,
                )

                thirdScreenState = ThirdScreenState(
                    name = it.name ?: "",
                    number = it.number ?: "",
                    types = it.types?.mapNotNull { type -> type } ?: emptyList(),
                    maxHP = it.maxHP ?: 0,
                    maxCP = it.maxCP ?: 0,
                    minWeight = it.weight?.minimum ?: "",
                    maxWeight = it.weight?.maximum ?: "",
                    minHeight = it.height?.minimum ?: "",
                    maxHeight = it.height?.maximum ?: ""
                )

                fourthScreenState = FourthScreenState(
                    resistant = it.resistant?.mapNotNull { resistant -> resistant } ?: emptyList(),
                    weaknesses = it.weaknesses?.mapNotNull { resistant -> resistant }
                        ?: emptyList(),
                    attacks = it.attacks?.let { attacks -> getPokemonAttacks(attacks) } ?: "",
                    evolutions = it.evolutions?.mapNotNull { evolution -> evolution } ?: emptyList()
                )
            }
        }
    }

    private fun getPokemonAttacks(attacks: PokemonByNameQuery.Attacks): String {
        val list: MutableList<String> = mutableListOf()
        attacks.fast?.map { item -> item?.name ?: "" }?.let {
            list.addAll(it)
        }
        attacks.special?.map { item -> item?.name ?: "" }?.let {
            list.addAll(it)
        }
        return list.joinToString()
    }

    fun textToSpeech(context: Context) {
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        firstScreenState.pokemons[selectedIndex.value].name,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
        }
    }

    fun handleDirectionalClick(direction: Direction) {
        when (direction) {
            Direction.UP -> {
                if (selectedIndex.value == 0) return
                selectedIndex.value -= 1
                noteIsVisibleState.value = true
                if (noteOffsetValue.value == NoteAnimationValue.START.step) {
                    noteOffsetValue.value =
                        NoteAnimationValue.FINISH.step
                } else {
                    noteOffsetValue.value =
                        NoteAnimationValue.START.step
                }
            }
            Direction.DOWN -> {
                if ((selectedIndex.value == firstScreenState.pokemons.size - 1)) return
                selectedIndex.value += 1
                noteIsVisibleState.value = true
                if (noteOffsetValue.value == NoteAnimationValue.START.step) {
                    noteOffsetValue.value =
                        NoteAnimationValue.FINISH.step
                } else {
                    noteOffsetValue.value =
                        NoteAnimationValue.START.step
                }
            }
            Direction.LEFT -> {
                if (pageIndex.value == 0) return
                pageIndex.value -= 1
            }
            Direction.RIGHT -> {
                if (pageIndex.value == Constants.MAX_PAGE_INDEX) return
                pageIndex.value += 1
            }
        }
    }
}