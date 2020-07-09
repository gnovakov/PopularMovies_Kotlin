package com.example.popularmovies_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.MovieApiStatus
import com.example.popularmovies_kotlin.api.MovieRepo
import com.example.popularmovies_kotlin.api.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val movieRepo: MovieRepo): ViewModel()  {

    // The most recent API response
    private val _apiStatus = MutableLiveData<MovieApiStatus>()
    val apiStatus: LiveData<MovieApiStatus>
        get() = _apiStatus

    // A Movie
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() = _movies

    // Holds the selected property data
    private val _navigateToSelectedMovie = MutableLiveData<Movie>()
    val navigateToSelectedMovie: LiveData<Movie>
        get() = _navigateToSelectedMovie

    private var viewModelJob = Job() // Coroutines Job

    // A coroutine scope for that new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

    init {
        getTopRatedMovies(MovieApiFilter.POPULAR_MOVIES)
    }


    private fun getTopRatedMovies(filter: MovieApiFilter) {
        // Using Coroutines
        coroutineScope.launch {
            var getMoviesDeferred = movieRepo.getTopRatedMovies(filter)
//            var getMoviesDeferred = MovieServiceApi.retrofitService
//                .getTopRatedMovies(BuildConfig.MOVIE_DATA_BASE_API, "en-us", filter.value,
//                    "false", "false", 1)
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                var apiResult = getMoviesDeferred.await()
                _apiStatus.value = MovieApiStatus.DONE
                _movies.value = apiResult.results
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                _movies.value = ArrayList()
            }
        }
    }

    // sets _navigateToSelectedProperty to the selected Mars property, when this is set the switch to detail page will happen
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    // sets _navigateToSelectedProperty back to null/resets it, this way when we return to overview page it doesn't automatically go back to the detail page
    fun displayMovieDetailsComplete() {
        _navigateToSelectedMovie.value = null
    }

    fun updateFilter(filter: MovieApiFilter) {
        getTopRatedMovies(filter)
    }

    // Cancel the Coroutines Job
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

enum class MovieApiFilter(val value: String) {
    POPULAR_MOVIES("popularity.desc"),
    TOP_RATED_MOVIES("vote_count.desc")
}