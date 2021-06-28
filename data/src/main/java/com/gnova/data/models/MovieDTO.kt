package com.gnova.data.models

import com.squareup.moshi.Json

data class MovieDTO(
    @Json(name = "id")
    val id : Int?,
    @Json(name = "vote_average")
    val vote_average : Double?,
    @Json(name = "title")
    val title : String?,
    @Json(name = "release_date")
    val release_date : String?,
    @Json(name = "backdrop_path")
    val backdrop_path : String?,
    @Json(name = "overview")
    val overview : String?,
    @Json(name = "poster_path")
    val poster_path : String?
)