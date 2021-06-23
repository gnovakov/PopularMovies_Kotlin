package com.gnova.domain.repositories

import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import io.reactivex.Single

interface MovieRepo {

    fun getTopRatedMovies(sort: String): Single<List<Movie>>

    fun getTrailers(id: Int): Single<List<Trailer>>

}