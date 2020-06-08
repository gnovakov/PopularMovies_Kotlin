package com.example.popularmovies_kotlin.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.popularmovies_kotlin.Const
import com.example.popularmovies_kotlin.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        @Suppress("UNUSED_VARIABLE")
        val application = requireNotNull(activity).application
        //Inflates the xml
        val binding = FragmentDetailBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner(this)

        // Grab the selectedProperty from the safeargs
        val movie = DetailFragmentArgs.fromBundle(arguments!!).selectedMovie

        // ViewModelFactory
        val viewModelFactory = DetailViewModelFactory(movie, application)

        binding.detailViewModel = ViewModelProvider(
            this, viewModelFactory).get(DetailViewModel::class.java)

        // Bind RecyclerView
        binding.trailerGrid.adapter = TrailerAdapter(TrailerAdapter.OnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(Const.YOUTUBE_TRAILER_BASE_URL + it.key)
            startActivity(openURL)
        })

        return binding.root
    }
}