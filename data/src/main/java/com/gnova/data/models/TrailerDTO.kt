package com.gnova.data.models

import com.squareup.moshi.Json

data class TrailerDTO(
    @Json(name = "id")
    val id: String,
    @Json(name = "key")
    val key: String,
    @Json(name = "name")
    val name: String
)