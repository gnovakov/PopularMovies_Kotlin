package com.example.popularmovies_kotlin.ui.home

import android.content.Context
import android.os.Bundle
import android.system.Os.remove
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
import com.example.popularmovies_kotlin.databinding.FragmentHomeBinding
import com.example.popularmovies_kotlin.ui.home.HomeViewState.*
import com.gnova.domain.models.Movie
import javax.inject.Inject

class HomeFragment : Fragment(R.layout.fragment_home) {


    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<HomeViewModel>
    private lateinit var viewModel: HomeViewModel
    private val adapter: MovieAdapter by lazy {
        MovieAdapter(MovieAdapter.OnClickListener {
            viewModel.displayMovieDetails(it) // Set the Movie to the _navigateToSelectedMovie Live Data
        })
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        val binding = FragmentHomeBinding.bind(view)
        _binding = binding


        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        setupRecyclerView()

        viewModel.onViewLoaded()

        observeviewState()
        observeClick()


    }

    private fun observeviewState() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    Log.d("TAG", "LOADING")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    Log.d("TAG", "ERROR HOME FRAGMENT")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                is Presenting -> {
                    binding.statusImage.visibility = View.GONE
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
        val newMovies: MutableList<Movie> = mutableListOf()

        movies.forEach {
            if( it.backdrop_path != null &&
                it.poster_path != null &&
                it.release_date != null ) {
                newMovies.add(it)
            }
        }

        adapter.submitList(newMovies)
    }


    private fun setupRecyclerView() {
        Log.d("TAG", "setupRecyclerView")
        binding.movieRecyclerView.setHasFixedSize(true)
        binding.movieRecyclerView.layoutManager = GridLayoutManager(this.context, 2)
        binding.movieRecyclerView.adapter = adapter
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}
