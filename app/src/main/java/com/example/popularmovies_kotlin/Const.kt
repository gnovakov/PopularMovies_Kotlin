package com.example.popularmovies_kotlin

object Const {

    private const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

    private const val IMAGE_SIZE_W342 = "w342"
    private const val IMAGE_SIZE_W185 = "w185"
    private const val IMAGE_SIZE_W154 = "w154"

    const val BASE_IMAGE_LARGE = BASE_IMAGE_URL + IMAGE_SIZE_W342
    const val BASE_IMAGE_MEDIUM = BASE_IMAGE_URL + IMAGE_SIZE_W185
    const val BASE_IMAGE_SMALL = BASE_IMAGE_URL + IMAGE_SIZE_W154

    const val YOUTUBE_THUMBNAIL_START_URL: String = "https://img.youtube.com/vi/"
    const val YOUTUBE_THUMBNAIL_END_URL: String = "/0.jpg"
    const val YOUTUBE_TRAILER_BASE_URL = "https://www.youtube.com/watch?v="

    const val BASE_URL = "https://api.themoviedb.org/3/"
}