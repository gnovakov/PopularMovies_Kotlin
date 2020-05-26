package com.example.popularmovies_kotlin.api

import com.example.popularmovies_kotlin.api.models.MoviesResult
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
https://api.themoviedb.org/3/movie/top_rated?api_key=1234567890&page=1
*/
interface MovieApi {

    @GET("3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): Deferred<MoviesResult>

}