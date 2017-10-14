package com.natan.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/{id}")
    fun fetchPokemon(@Path("id") pokemonId: Int): Call<Pokemon>
}