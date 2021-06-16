package com.example.popularmovies_kotlin.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.popularmovies_kotlin.api.MovieRepo
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.ui.home.HomeViewState.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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


    fun onViewLoaded() {
        getTopRatedMovies(MovieApiFilter.POPULAR_MOVIES)
    }

    private fun getTopRatedMovies(filter: MovieApiFilter) {
        _viewState.value = Loading
        add(
            movieRepo.getTopRatedMovies(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _viewState.value = Presenting(it.results)
                }, {
                    _viewState.value = Error
                }

                ))
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

    val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun add(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}

enum class MovieApiFilter(val value: String) {
    POPULAR_MOVIES("popularity.desc"),
    TOP_RATED_MOVIES("vote_count.desc")
}