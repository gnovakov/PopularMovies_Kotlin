package com.gnova.domain.models

import java.io.Serializable

data class Movie(
    val id : Int,
    val vote_average : Double,
    val title : String,
    val release_date : String = "",
    val backdrop_path : String,
    val overview : String,
    val poster_path : String
) : Serializable