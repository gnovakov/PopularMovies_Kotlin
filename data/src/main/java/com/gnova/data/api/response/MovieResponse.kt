package com.gnova.data.api.response

import com.gnova.data.models.MovieDTO
import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "page")
    val page: Int,
    @Json(name = "total_results")
    val total_results: Int,
    @Json(name = "total_pages")
    val total_pages: Int,
    @Json(name = "results")
    val results: List<MovieDTO>
)