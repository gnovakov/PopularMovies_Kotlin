package com.example.popularmovies_kotlin.api

import com.example.popularmovies_kotlin.BuildConfig
import com.example.popularmovies_kotlin.api.models.MoviesResult
import com.example.popularmovies_kotlin.api.models.TrailersResult
import com.example.popularmovies_kotlin.ui.home.MovieApiFilter
import com.gnova.data.api.MovieApi
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class MovieRepo @Inject constructor(private val movieApi: MovieApi) {

    fun getTopRatedMovies(filter: MovieApiFilter): Deferred<MoviesResult> =
        movieApi.getTopRatedMovies(
                BuildConfig.MOVIE_DATA_BASE_API, "en-us", filter.value,
                "false", "false", 1
            )

    fun getTrailers(id: Int): Deferred<TrailersResult> =
        movieApi.getTrailers(
                id,
                BuildConfig.MOVIE_DATA_BASE_API, "en-us")
    }