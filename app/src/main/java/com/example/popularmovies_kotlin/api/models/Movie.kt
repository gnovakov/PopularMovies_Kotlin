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
    val popularity : Double,
    val id : Int,
    val video : Boolean,
    val vote_count : Int,
    val vote_average : Double,
    val title : String,
    val release_date : String = "",
    val original_language : String,
    val original_title : String,
    val genre_ids : List<Int>,
    val backdrop_path : String,
    val adult : Boolean,
    val overview : String,
    val poster_path : String
) : Parcelable

