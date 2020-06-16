package com.example.popularmovies_kotlin.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreditsResult(
    val id : Int,
    val cast : List<Cast>
) : Parcelable

@Parcelize
data class Cast(
    val cast_id : Int,
    val character : String,
    val credit_id : String,
    val gender : Int,
    val id : Int,
    val name : String,
    val order : Int,
    val profile_path : String
) : Parcelable