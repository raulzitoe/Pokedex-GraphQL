package com.example.pokedexgraphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.okHttpClient
import com.example.pokedexgraphql.graphql.PokemonByNameQuery
import com.example.pokedexgraphql.graphql.PokemonDBQuery
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object GraphQLManager {
    private val client = OkHttpClient.Builder()

    fun getInstance(): ApolloClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        if(BuildConfig.DEBUG) client.addInterceptor(logging)
        return ApolloClient.Builder().serverUrl("https://graphql-pokemon2.vercel.app/")
            .okHttpClient(client.build())
            .build()
    }

    suspend fun getPokemons(): ApolloResponse<PokemonDBQuery.Data>{
        return getInstance().query(PokemonDBQuery()).execute()
    }

    suspend fun getPokemonByName(name: String): ApolloResponse<PokemonByNameQuery.Data>{
        return getInstance().query(PokemonByNameQuery(Optional.Present(name))).execute()
    }
}