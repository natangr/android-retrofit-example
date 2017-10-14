package com.natan.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val BASE_URL = "https://pokeapi.co/api/v2/"

    private var retrofit: Retrofit? = null
    private var usingAuth = false

    fun getRetrofit(useAuth: Boolean = false): Retrofit? {
        if (retrofit == null || usingAuth != useAuth) {
            rebuildRetrofit(useAuth)
        }
        return retrofit
    }

    //region Private

    private fun rebuildRetrofit(useAuth: Boolean) {
        val client = buildOkHttpClient(useAuth)
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .client(client)
                .build()
    }

    private fun buildGson(): Gson {
        return GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
    }

    private fun buildOkHttpClient(useAuth: Boolean): OkHttpClient {
        usingAuth = useAuth
        val builder = OkHttpClient.Builder()
        addLoggingInterceptor(builder)
        if (useAuth) {
            addTokenInterceptor(builder)
        }

        return builder.build()
    }

    private fun addTokenInterceptor(builder: OkHttpClient.Builder) {
        // This automatically adds the accessToken for any requests if the @useAuth parameter is true and if there' any accessToken
        val tokenInterceptor = Interceptor { chain ->

            val request = chain.request()
            var newRequest = request
            // TODO: replace tokens below
            val accessToken = "REPLACE_THIS_WITH_TOKEN"
            val tokenType = "REPLACE_THIS_WITH_TOKEN_TYPE"
            newRequest = newRequest.newBuilder().addHeader("Authorization", "$tokenType $accessToken").build()
            chain.proceed(newRequest)
        }
        builder.addInterceptor(tokenInterceptor)
    }

    private fun addLoggingInterceptor(builder: OkHttpClient.Builder) {
        // This adds logging for all requests if the app is run in Debug mode
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addNetworkInterceptor(httpLoggingInterceptor)
        }
    }

    //endregion
}
