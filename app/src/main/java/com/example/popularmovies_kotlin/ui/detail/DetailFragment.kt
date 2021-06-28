package com.example.popularmovies_kotlin.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovies_kotlin.App
import com.example.popularmovies_kotlin.Const.BASE_IMAGE_LARGE
import com.example.popularmovies_kotlin.Const.YOUTUBE_TRAILER_BASE_URL
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.ViewModelFactory
import com.example.popularmovies_kotlin.databinding.FragmentDetailBinding
import com.example.popularmovies_kotlin.ui.detail.DetailViewState.*
import com.gnova.domain.models.Movie
import com.gnova.domain.models.Trailer
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailFragment : Fragment(R.layout.fragment_detail) {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<DetailViewModel>
    private lateinit var viewModel: DetailViewModel
    private val trailerViewAdapter: TrailerViewAdapter by lazy {
        TrailerViewAdapter(TrailerViewAdapter.OnClickListener {
            trailerClick(it)
        })
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
        val binding = FragmentDetailBinding.bind(view)
        _binding = binding

        // Grab the selectedMovie from the safeargs
        val movie = DetailFragmentArgs.fromBundle(requireArguments()).selectedMovie

        // Init View Model
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.onViewInit(movie)

        setupRecyclerView()

        observeviewState()

    }

    private fun observeviewState() {

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    Log.d("TAG", "LOADING DF")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                }
                is Error -> {
                    Log.d("TAG", "ERROR")
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
                is Presenting -> {
                    Log.d("TAG", "DONE DF")
                    observeSelectedMovie()
                    showTrailers(it.results)
                }

            }
        })

    }

    private fun observeSelectedMovie() {
        viewModel.selectedMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                initialiseData(it)
            }
        })
    }

    private fun showTrailers(trailers: List<Trailer>) {
        Log.d("TAG", "showTrailers TRAILER " + trailers[0].name)
        trailerViewAdapter.submitList(trailers)
    }

    private fun initialiseData(movie: Movie) {
        movie.poster_path?.let { picassoLoadImages(it, binding.moviePoster) }
        movie.backdrop_path?.let { picassoLoadImages(it, binding.movieBackdrop) }
        binding.releaseDate.text = movie.release_date
        binding.rating.text = movie.vote_average.toString()
        binding.movieTitle.text = movie.title
        binding.synopsis.text = movie.overview
    }

    private fun trailerClick(it: Trailer) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(YOUTUBE_TRAILER_BASE_URL + it.key)
        startActivity(openURL)
    }

    private fun setupRecyclerView() {
        binding.trailerRecyclerView.setHasFixedSize(true)
        binding.trailerRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.trailerRecyclerView.adapter = trailerViewAdapter
    }
    

    private fun picassoLoadImages(img: String, imageView: ImageView) {
        val imgUrl = BASE_IMAGE_LARGE + img
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Picasso.get()
                .load(imgUri)
                //.placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageView)
        }

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}