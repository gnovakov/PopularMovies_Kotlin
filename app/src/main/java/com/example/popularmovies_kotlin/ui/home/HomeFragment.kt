package com.example.popularmovies_kotlin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popularmovies_kotlin.Const.BASE_IMAGE_SMALL
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.api.MovieApiFilter
import com.example.popularmovies_kotlin.api.MovieApiStatus
import com.example.popularmovies_kotlin.api.models.Movie
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private val adapter: MovieAdapter by lazy {
        MovieAdapter()
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        setupRecyclerView()

       setHasOptionsMenu(true)

        observeApiStatus()
    }

    private fun observeApiStatus() {
        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    MovieApiStatus.LOADING -> {
                        status_image.visibility = View.VISIBLE
                        status_image.setImageResource(R.drawable.loading_animation)
                    }
                    MovieApiStatus.ERROR -> {
                        status_image.visibility = View.VISIBLE
                        status_image.setImageResource(R.drawable.ic_connection_error)
                    }
                    MovieApiStatus.DONE -> {
                        status_image.visibility = View.GONE
                        observeMovies()
                    }

                }
            }
        })
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            it?.let {
                Log.d("TAG", BASE_IMAGE_SMALL+it[0].poster_path)
                showMovies(it)
            }
        })
    }



    private fun showMovies(movies: List<Movie>) {
        adapter.submitList(movies)
    }


    private fun setupRecyclerView() {
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager = GridLayoutManager(activity, 2)
        movie_recycler_view.adapter = adapter
    }



    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.popular_movies -> MovieApiFilter.POPULAR_MOVIES
                R.id.top_rated_movies -> MovieApiFilter.TOP_RATED_MOVIES
                else -> MovieApiFilter.POPULAR_MOVIES
            }
        )
        return true
    }



}
