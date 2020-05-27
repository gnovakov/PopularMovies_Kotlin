package com.example.popularmovies_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.BuildConfig
import com.example.popularmovies_kotlin.Const
import com.example.popularmovies_kotlin.api.MovieServiceApi
import com.example.popularmovies_kotlin.api.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // The most recent API response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // A Movie
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
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
        getMovies()
    }


    private fun getMovies() {

        // Using Coroutines
        coroutineScope.launch {
            var getPropertiesDeferred = MovieServiceApi.retrofitService
                .getTopRatedMovies(BuildConfig.MOVIE_DATA_BASE_API, 1, "GB")
            try {
                var apiResult = getPropertiesDeferred.await()
                _response.value = "Success: ${apiResult.results.size} Movies retrieved"
                if (apiResult.results.size > 0) {
                    _movie.value = apiResult.results[0]
                    _moviePoster.value = Const.BASE_IMAGE_LARGE + movie.value?.poster_path
                }
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }

    // Cancel the Coroutines Job
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}