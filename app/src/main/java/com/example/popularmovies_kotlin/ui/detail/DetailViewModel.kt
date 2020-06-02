package com.example.popularmovies_kotlin.ui.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.popularmovies_kotlin.api.models.Movie

class DetailViewModel(movie: Movie, app: Application) : AndroidViewModel(app) {

    // MutableLiveData that stores the selected movie
    private val _selectedMovie = MutableLiveData<Movie>()

    // The external immutable LiveData for the selected movie
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }
}