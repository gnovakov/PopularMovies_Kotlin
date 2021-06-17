package com.example.popularmovies_kotlin.ui.home

import com.gnova.domain.models.Movie


sealed class HomeViewState {

    data class Presenting( val results: List<Movie>) : HomeViewState()

    object Error : HomeViewState()

    object Loading : HomeViewState()
}