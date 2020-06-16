package com.example.popularmovies_kotlin.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies_kotlin.api.models.Movie

class DetailViewModelFactory( private val movie: Movie, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(movie, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}