package com.example.popularmovies_kotlin.ui.home

import com.example.popularmovies_kotlin.api.models.Movie

sealed class HomeViewState {

    data class Presenting( val results: List<Movie>) : HomeViewState()

    object Error : HomeViewState()

    object Loading : HomeViewState()
}