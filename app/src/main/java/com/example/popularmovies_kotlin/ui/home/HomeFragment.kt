package com.example.popularmovies_kotlin.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.popularmovies_kotlin.App
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.ViewModelFactory
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.ui.home.HomeViewState.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : Fragment() {


    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it) // Set the Movie to the _navigateToSelectedMovie Live Data
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        setupRecyclerView()

        observeviewState()
        observeClick()

        setHasOptionsMenu(true)
    }

    private fun observeviewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    Log.d("TAG", "LOADING")
                    status_image.visibility = View.VISIBLE
                    status_image.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    Log.d("TAG", "ERROR")
                    status_image.visibility = View.VISIBLE
                    status_image.setImageResource(R.drawable.ic_connection_error)
                }
                is Presenting -> {
                    status_image.visibility = View.GONE
                    showMovies(it.results)
                }

            }
        })
    }

    private fun observeClick() {
        viewModel.navigateToSelectedMovie.observe(viewLifecycleOwner, Observer {
            if (it != null) { //Open the Detail Fragment if _navigateToSelectedMovie is not Null
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                )
                viewModel.displayMovieDetailsComplete() // Clear the _navigateToSelectedMovie after the Detail fragment is opened
            }
        })
    }



    private fun showMovies(movies: List<Movie>) {
        adapter.submitList(movies)
    }


    private fun setupRecyclerView() {
        Log.d("TAG", "setupRecyclerView")
        movie_recycler_view.setHasFixedSize(true)
        movie_recycler_view.layoutManager = GridLayoutManager(this.context, 2)
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
