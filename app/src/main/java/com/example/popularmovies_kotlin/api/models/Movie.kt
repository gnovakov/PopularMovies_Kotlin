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
    val vote_count: Int,
    val id: Int,
    val video: Boolean = false,
    val vote_average: Double,
    val title: String,
    val popularity: Double,
    val poster_path: String,
    val original_language: String,
    val original_title: String,
    val genre_ids: List<Int> = emptyList(),
    val backdrop_path: String,
    val adult: Boolean = false,
    val overview: String,
    val release_date: String
) : Parcelable