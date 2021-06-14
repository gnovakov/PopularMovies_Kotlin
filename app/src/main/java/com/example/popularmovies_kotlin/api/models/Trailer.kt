package com.example.popularmovies_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TrailersResult(
    val id: Int,
    val results: List<Trailer>
) : Parcelable

@Parcelize
data class Trailer(
    val id: String,
    val key: String,
    val name: String
) : Parcelable