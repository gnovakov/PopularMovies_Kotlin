package com.example.popularmovies_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.BuildConfig
import com.example.popularmovies_kotlin.api.MovieServiceApi
import com.example.popularmovies_kotlin.api.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class MovieApiStatus { LOADING, ERROR, DONE }

class HomeViewModel : ViewModel() {

    // The most recent API response
    private val _status = MutableLiveData<MovieApiStatus>()
    val status: LiveData<MovieApiStatus>
        get() = _status

    // A Movie
    private val _movie = MutableLiveData<List<Movie>>()
    val movie: LiveData<List<Movie>>
        get() = _movie

    // A Movie Poster Url
    private val _moviePoster = MutableLiveData<String>()
    val moviePoster: LiveData<String>
        get() = _moviePoster

    private var viewModelJob = Job() // Coroutines Job

    // A coroutine scope for that new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    init {
        getTopRatedMovies()
    }


    private fun getTopRatedMovies() {

        // Using Coroutines
        coroutineScope.launch {
            var getPropertiesDeferred = MovieServiceApi.retrofitService
                .getTopRatedMovies(BuildConfig.MOVIE_DATA_BASE_API, 1, "GB")
            try {
                _status.value = MovieApiStatus.LOADING
                var apiResult = getPropertiesDeferred.await()
                _status.value = MovieApiStatus.DONE
                _movie.value = apiResult.results
            } catch (e: Exception) {
                _status.value = MovieApiStatus.ERROR
                _movie.value = ArrayList()
            }
        }
    }

    // Cancel the Coroutines Job
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}