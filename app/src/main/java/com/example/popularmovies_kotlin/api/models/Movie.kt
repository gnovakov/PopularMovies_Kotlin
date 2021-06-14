package com.example.popularmovies_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesResult(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<Movie>
) : Parcelable

@Parcelize
data class Movie(
    val id : Int,
    val vote_average : Double,
    val title : String,
    val release_date : String = "",
    val backdrop_path : String,
    val overview : String,
    val poster_path : String
) : Parcelable

