package com.example.popularmovies_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.BuildConfig
import com.example.popularmovies_kotlin.api.MovieServiceApi
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.api.models.MoviesResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    // The most recent API response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // A Movie
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

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
                var result = getPropertiesDeferred.await()
                _response.value = "Success: ${result.results.size} Movies retrieved"
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