package com.example.pokedexgraphql.ui.screens.home

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexgraphql.GraphQLManager
import com.example.pokedexgraphql.NotePosition
import com.example.pokedexgraphql.graphql.PokemonByNameQuery
import com.example.pokedexgraphql.graphql.PokemonDBQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {
    val pokemons: MutableState<List<PokemonDBQuery.Pokemon>> = mutableStateOf(emptyList())
    var selectedIndex = mutableStateOf(0)
    var pageIndex = mutableStateOf(1)
    val listState = LazyListState(0, 0)
    val pokemon: MutableState<PokemonByNameQuery.Pokemon> = mutableStateOf(
        PokemonByNameQuery.Pokemon(
            name = null,
            weight = null,
            height = null,
            image = null,
            id = "",
            number = null,
            classification = null,
            types = null,
            resistant = null,
            attacks = null,
            weaknesses = null,
            fleeRate = null,
            maxCP = null,
            evolutions = null,
            evolutionRequirements = null,
            maxHP = null
        )
    )
    var noteState = mutableStateOf(NotePosition.Start)
    var noteOffsetValue = mutableStateOf(NoteAnimationValue.START.step)
    private  var  textToSpeech:TextToSpeech? = null

    enum class NoteAnimationValue(val step: Dp){
        START(2.dp), FINISH(20.dp)
    }

    fun getPokemons() {
        viewModelScope.launch {
            GraphQLManager.getPokemons().data?.pokemons?.mapNotNull { it }?.let {
                pokemons.value = it
            }
        }
    }

    fun getPokemonByName(name: String) {
        viewModelScope.launch {
            GraphQLManager.getPokemonByName(name).data?.pokemon?.let {
                pokemon.value = it
            }
        }
    }

    fun getPokemonAttacks(): String {
        val list: MutableList<String> = mutableListOf()
        pokemon.value.attacks?.fast?.map { item -> item?.name ?: "" }?.let {
            list.addAll(it)
        }
        pokemon.value.attacks?.special?.map { item -> item?.name ?: "" }?.let {
            list.addAll(it)
        }
        return list.joinToString()
    }

    fun animateScroll() {
        viewModelScope.launch {
            listState.animateScrollToItem(selectedIndex.value)
        }
    }

    fun clearPokemon() {
        pokemon.value = PokemonByNameQuery.Pokemon(
            name = null,
            weight = null,
            height = null,
            image = null,
            id = "",
            number = null,
            classification = null,
            types = null,
            resistant = null,
            attacks = null,
            weaknesses = null,
            fleeRate = null,
            maxCP = null,
            evolutions = null,
            evolutionRequirements = null,
            maxHP = null
        )
    }

    fun textToSpeech(context: Context){
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    txtToSpeech.language = Locale.US
                    txtToSpeech.setSpeechRate(1.0f)
                    txtToSpeech.speak(
                        pokemons.value[selectedIndex.value].name,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        null
                    )
                }
            }
        }
    }
}