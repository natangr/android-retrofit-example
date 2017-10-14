package com.natan.retrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchRandomPokemon()
    }

    private fun fetchRandomPokemon() {
        val randomId = Random().nextInt(151) + 1
        fetchPokemonWithId(randomId)
    }

    private fun fetchPokemonWithId(pokemonId: Int) {
        val retrofit = RetrofitHelper.getRetrofit()
        val pokemonApi = retrofit?.create(PokemonApi::class.java)
        pokemonApi?.fetchPokemon(pokemonId)?.enqueue(object: Callback<Pokemon>{
            override fun onResponse(call: Call<Pokemon>?, response: Response<Pokemon>?) {
                Log.d("Response", "Pokemon: ${response?.body()?.name}")
            }

            override fun onFailure(call: Call<Pokemon>?, t: Throwable?) {
                Log.e("Failure", t?.localizedMessage, t)
            }
        })
    }
}
