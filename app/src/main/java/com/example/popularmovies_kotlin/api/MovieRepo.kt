package com.example.popularmovies_kotlin.api

import com.example.popularmovies_kotlin.BuildConfig
import com.example.popularmovies_kotlin.api.models.MoviesResult
import com.example.popularmovies_kotlin.api.models.TrailersResult
import com.example.popularmovies_kotlin.ui.home.MovieApiFilter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class MovieRepo @Inject constructor(private val movieApi: MovieApi) {

    fun getTopRatedMovies(filter: MovieApiFilter): Single<MoviesResult> =
        movieApi.getTopRatedMovies(
                BuildConfig.MOVIE_DATA_BASE_API, "en-us", filter.value,
                "false", "false", 1
            )

    fun getTrailers(id: Int): Single<TrailersResult> =
        movieApi.getTrailers(
                id,
                BuildConfig.MOVIE_DATA_BASE_API, "en-us")

    }