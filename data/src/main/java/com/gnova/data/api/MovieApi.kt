package com.gnova.data.api

import com.gnova.data.api.response.MovieResponse
import com.gnova.data.api.response.TrailersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("discover/movie")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("include_adult") includeAdult: String,
        @Query("include_video") includeVideo: String,
        @Query("page") page: Int
    ): Single<MovieResponse>

    @GET("movie/{movie_id}/videos")
    fun getTrailers(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<TrailersResponse>
}