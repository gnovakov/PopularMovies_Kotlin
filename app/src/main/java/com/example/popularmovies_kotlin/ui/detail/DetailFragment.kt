package com.example.popularmovies_kotlin.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovies_kotlin.Const.BASE_IMAGE_LARGE
import com.example.popularmovies_kotlin.Const.YOUTUBE_TRAILER_BASE_URL
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.api.MovieApiStatus
import com.example.popularmovies_kotlin.api.models.Movie
import com.example.popularmovies_kotlin.api.models.Trailer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel

    private val trailerAdapter: TrailerAdapter by lazy {
        TrailerAdapter(TrailerAdapter.OnClickListener {
            trailerClick(it)
        })
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Grab the selectedProperty from the safeargs
        val movie = DetailFragmentArgs.fromBundle(arguments!!).selectedMovie
        // ViewModelFactory
        val viewModelFactory = DetailViewModelFactory(movie)
        // Init View Model
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)

        setupRecyclerView()

        observeApiStatus()

    }

    private fun observeApiStatus() {
        viewModel.apiStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    MovieApiStatus.LOADING -> {
                        Log.d("TAG", "LOADING DF")
                        status_image.visibility = View.VISIBLE
                        status_image.setImageResource(R.drawable.loading_animation)
                    }
                    MovieApiStatus.ERROR -> {
                        Log.d("TAG", "ERROR DF")
//                        status_image.visibility = View.VISIBLE
//                        status_image.setImageResource(R.drawable.ic_connection_error)
                    }
                    MovieApiStatus.DONE -> {
                        Log.d("TAG", "DONE DF")
                        //status_image.visibility = View.GONE
                        observeSelectedMovie()
                        observeTrailers()
                    }

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

    private fun observeTrailers() {
        viewModel.trailers.observe(viewLifecycleOwner, Observer {
            it?.let {
                showTrailers(it)
            }
        })
    }

    private fun showTrailers(trailers: List<Trailer>) {
        Log.d("TAG", "showTrailers TRAILER " + trailers[0].name)
        trailerAdapter.submitList(trailers)
    }

    private fun initialiseData(movie: Movie) {
        picassoLoadImages(movie.poster_path, movie_poster)
        picassoLoadImages(movie.backdrop_path, movie_backdrop)
        release_date.text = movie.release_date
        rating.text = movie.vote_average.toString()
        movie_title.text = movie.title
        synopsis.text = movie.overview
    }

    private fun trailerClick(it: Trailer) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(YOUTUBE_TRAILER_BASE_URL + it.key)
        startActivity(openURL)
    }

    private fun setupRecyclerView() {
        trailer_recycler_view.setHasFixedSize(true)
        trailer_recycler_view.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        trailer_recycler_view.adapter = trailerAdapter
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

}