package com.example.popularmovies_kotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies_kotlin.Const.BASE_IMAGE_LARGE
import com.example.popularmovies_kotlin.R
import com.gnova.domain.models.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_grid_view_item.view.*

class MovieAdapter(private val onClickListener: OnClickListener) : ListAdapter<Movie, MovieAdapter.MovieHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_view_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val movies = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(movies)
        }
        holder.bind(movies)
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {

            val imgUrl = BASE_IMAGE_LARGE + movie.poster_path
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(itemView.movieImage)
            }

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    class OnClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }

}