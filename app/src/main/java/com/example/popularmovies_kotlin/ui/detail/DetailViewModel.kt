package com.example.popularmovies_kotlin.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popularmovies_kotlin.MovieApiStatus
import com.example.popularmovies_kotlin.api.MovieRepo
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.api.models.Trailer
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

    private fun getTrailers(id: Int) {
        // Using Coroutines
        viewModelScope.launch {
            var getTrailersDeferred = movieRepo.getTrailers(id)
//                MovieServiceApi.retrofitService
//                    .getTrailers(
//                        id, BuildConfig.MOVIE_DATA_BASE_API, "en-us")
            try {
                _apiStatus.value = MovieApiStatus.LOADING
                Log.d("TAG", "MovieApiStatus LOADING TRAILERS VM")
                var apiResultTrailer = getTrailersDeferred.await()
                _apiStatus.value = MovieApiStatus.DONE
                Log.d("TAG", "MovieApiStatus DONE TRAILERS VM")
                _trailers.value = apiResultTrailer.results
            } catch (e: Exception) {
                _apiStatus.value = MovieApiStatus.ERROR
                _trailers.value = ArrayList()
            }
        }
    }

}