package com.example.popularmovies_kotlin.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.MovieApiStatus
import com.example.popularmovies_kotlin.api.MovieRepo
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.api.models.Trailer
import com.example.popularmovies_kotlin.ui.detail.DetailViewState.*
import com.example.popularmovies_kotlin.ui.home.HomeViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(private val movieRepo: MovieRepo) : ViewModel() {

    fun onViewInit(movie: Movie) {
        _selectedMovie.value = movie
        _movieId.value = movie.id
        movieId.value?.let { getTrailers(it) }
    }

    // MutableLiveData that stores the selected movie
    private val _selectedMovie = MutableLiveData<Movie>()
    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    // View State
    private val _viewState = MutableLiveData<DetailViewState>()
    val viewState: LiveData<DetailViewState>
        get() = _viewState

    // The most recent API response
    private val _apiStatus = MutableLiveData<MovieApiStatus>()
    val apiStatus: LiveData<MovieApiStatus>
        get() = _apiStatus

    // ID to get the Trailers and the Reviews.
    private val _movieId = MutableLiveData<Int>()
    val movieId: LiveData<Int>
        get() = _movieId

    // A Trailer
    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>>
        get() = _trailers

    private var viewModelJob = Job() // Coroutines Job

    // A coroutine scope for that new job using the main dispatcher
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main )

//    private fun getTrailers(id: Int) {
//
//        // Using Coroutines
//        coroutineScope.launch {
//            var getTrailersDeferred = movieRepo.getTrailers(id)
//
//            try {
//                _apiStatus.value = MovieApiStatus.LOADING
//                Log.d("TAG", "MovieApiStatus LOADING TRAILERS VM")
//                var apiResultTrailer = getTrailersDeferred.await()
//                _apiStatus.value = MovieApiStatus.DONE
//                Log.d("TAG", "MovieApiStatus DONE TRAILERS VM")
//                _trailers.value = apiResultTrailer.results
//            } catch (e: Exception) {
//                _apiStatus.value = MovieApiStatus.ERROR
//                _trailers.value = ArrayList()
//            }
//        }
//    }

    private fun getTrailers(id: Int) {
        // Using Coroutines
        coroutineScope.launch {
            var getTrailersDeferred = movieRepo.getTrailers(id)

            try {
                _viewState.value = Loading
                var apiResultTrailer = getTrailersDeferred.await()
                _viewState.value = Presenting(apiResultTrailer.results)
            } catch (e: Exception) {
                _viewState.value = Error
            }
        }
    }
    // Cancel the Coroutines Job
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}