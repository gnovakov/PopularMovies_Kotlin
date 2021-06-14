package com.gnova.domain.repositories

import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import kotlinx.coroutines.Deferred

interface MovieRepo {

    fun getTopRatedMovies(filter: String): Deferred<Movie>

    fun getTrailers(id: Int): Deferred<Trailer>

}