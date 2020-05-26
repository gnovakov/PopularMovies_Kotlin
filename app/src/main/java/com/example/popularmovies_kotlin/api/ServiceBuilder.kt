package com.example.popularmovies_kotlin.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://api.themoviedb.org/"

// Instantiate Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Create the Retrofit Builder
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory()) // Needed for Coroutines
    .baseUrl(BASE_URL)
    .build()

// Initialize the Retrofit service
object MovieServiceApi {
    val retrofitService : MovieApi by lazy {
        retrofit.create(MovieApi::class.java) }
}