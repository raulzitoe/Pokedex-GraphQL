package com.example.pokedexgraphql.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexgraphql.GraphQLManager
import com.example.pokedexgraphql.graphql.PokemonByNameQuery
import com.example.pokedexgraphql.ui.state.*
import com.example.pokedexgraphql.utils.Constants
import com.example.pokedexgraphql.utils.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor() : ViewModel() {
    private var textToSpeech: TextToSpeech? = null
    var pokedexScreenState by mutableStateOf(PokedexScreenState())
        private set

    init {
        getPokemons()
    }

    enum class NoteAnimationValue(val step: Dp) {
        START(2.dp), FINISH(20.dp)
    }

    private fun getPokemons() {
        viewModelScope.launch {
            GraphQLManager.getPokemons().data?.pokemons?.mapNotNull { it }?.let {
                pokedexScreenState.firstScreenState.value = FirstScreenState(it)
            }
        }
    }

    fun getPokemonByName() {
        val name =
            pokedexScreenState.firstScreenState.value.pokemons[pokedexScreenState.selectedIndex].name
                ?: ""
        viewModelScope.launch {
            GraphQLManager.getPokemonByName(name).data?.pokemon?.let {
                pokedexScreenState.secondScreenState.value = SecondScreenState(
                    name = it.name ?: "",
                    classification = it.classification ?: "",
                    image = it.image,
                )

                pokedexScreenState.thirdScreenState.value = ThirdScreenState(
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

                pokedexScreenState.fourthScreenState.value = FourthScreenState(
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

    private fun textToSpeech(context: Context) {
        val time = System.currentTimeMillis()
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                Log.e("asd", (System.currentTimeMillis() - time).toString())
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        pokedexScreenState.firstScreenState.value.pokemons[pokedexScreenState.selectedIndex].name,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                }
            }
        }
    }

    fun handleDirectionalClick(direction: Direction, context: Context) {
        when (direction) {
            Direction.UP -> {
                if (pokedexScreenState.selectedIndex == 0 || pokedexScreenState.pageIndex.value != 0) return
                pokedexScreenState.selectedIndex -= 1
                pokedexScreenState.noteIsVisibleState.value = true
                if (pokedexScreenState.noteOffsetValue.value == NoteAnimationValue.START.step) {
                    pokedexScreenState.noteOffsetValue.value =
                        NoteAnimationValue.FINISH.step
                } else {
                    pokedexScreenState.noteOffsetValue.value =
                        NoteAnimationValue.START.step
                }
                textToSpeech(context)
            }
            Direction.DOWN -> {
                if ((pokedexScreenState.selectedIndex == pokedexScreenState.firstScreenState.value.pokemons.size - 1)
                    || pokedexScreenState.pageIndex.value != 0) return
                pokedexScreenState.selectedIndex += 1
                pokedexScreenState.noteIsVisibleState.value = true
                if (pokedexScreenState.noteOffsetValue.value == NoteAnimationValue.START.step) {
                    pokedexScreenState.noteOffsetValue.value =
                        NoteAnimationValue.FINISH.step
                } else {
                    pokedexScreenState.noteOffsetValue.value =
                        NoteAnimationValue.START.step
                }
                textToSpeech(context)
            }
            Direction.LEFT -> {
                if (pokedexScreenState.pageIndex.value == 0) return
                pokedexScreenState.pageIndex.value -= 1
            }
            Direction.RIGHT -> {
                if (pokedexScreenState.pageIndex.value == Constants.MAX_PAGE_INDEX) return
                pokedexScreenState.pageIndex.value += 1
            }
        }
    }
}