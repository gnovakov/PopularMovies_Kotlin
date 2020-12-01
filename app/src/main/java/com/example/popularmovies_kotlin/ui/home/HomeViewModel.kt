package com.example.popularmovies_kotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.api.MovieRepo
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.ui.home.HomeViewState.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val movieRepo: MovieRepo): ViewModel()  {


    // View State
    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    // Holds the selected Movie data
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
            try {
                _viewState.value = Loading
                var apiResult = getMoviesDeferred.await()
                _viewState.value = Presenting(apiResult.results)
            } catch (e: Exception) {
                _viewState.value = Error
            }
        }
    }

    // sets _navigateToSelectedMovie to the selected Mars Movie, when this is set the switch to detail page will happen
    fun displayMovieDetails(movie: Movie) {
        _navigateToSelectedMovie.value = movie
    }

    // sets _navigateToSelectedMovie back to null/resets it, this way when we return to overview page it doesn't automatically go back to the detail page
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