package com.example.popularmovies_kotlin.api

import com.example.popularmovies_kotlin.api.models.MoviesResult
import com.example.popularmovies_kotlin.api.models.TrailersResult
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: String,
        @Query("include_video") include_video: String,
        @Query("page") page: Int
    ): Single<MoviesResult>

    @GET("movie/{MOVIE_ID}/videos")
    fun getTrailers(
        @Path("MOVIE_ID") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<TrailersResult>
}