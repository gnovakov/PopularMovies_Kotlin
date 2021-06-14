package com.gnova.data.repositories

import com.gnova.data.api.MovieApi
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.gnova.domain.repositories.MovieRepo
import kotlinx.coroutines.Deferred
import javax.inject.Inject

class MovieRepoImpl@Inject constructor(private val movieApi: MovieApi) : MovieRepo {

    override fun getTopRatedMovies(filter: String): Deferred<Movie> {
        TODO("Not yet implemented")
    }

    override fun getTrailers(id: Int): Deferred<Trailer> {
        TODO("Not yet implemented")
    }
}

//override fun getTopRatedMovies(filter: MovieApiFilter): Deferred<MoviesResult> =
//    movieApi.getTopRatedMovies(
//        BuildConfig.MOVIE_DATA_BASE_API, "en-us", filter.value,
//        "false", "false", 1
//    )
//
//override fun getTrailers(id: Int): Deferred<TrailersResult> =
//    movieApi.getTrailers(
//        id,
//        BuildConfig.MOVIE_DATA_BASE_API, "en-us")