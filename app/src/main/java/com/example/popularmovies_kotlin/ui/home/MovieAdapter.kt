package com.example.popularmovies_kotlin.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.popularmovies_kotlin.Const.BASE_IMAGE_LARGE
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.api.models.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_grid_view_item.view.*


class MovieAdapter() : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var movies: List<Movie> = listOf()

    override fun getItemCount(): Int = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_view_item, parent, false)
        return MovieHolder(view)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun setData(data: List<Movie>) {
        movies = data
        notifyDataSetChanged()
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            Picasso.get()
                .load(BASE_IMAGE_LARGE + movie.poster_path)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(itemView.movieImage)

//            val imgUrl = BASE_IMAGE_SMALL + movie.poster_path
//            imgUrl?.let {
//                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
//                Glide.with(itemView.context)
//                    .load(imgUri)
//                    .apply(
//                        RequestOptions()
//                            .placeholder(R.drawable.loading_animation)
//                            .error(R.drawable.ic_broken_image))
//                    .into(itemView.movieImage)
//            }

        }
    }

}

/*class MovieAdapter() : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(DiffCallback) {

    private var movies: List<Movie> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid_view_item, parent, false)
        return MovieAdapter.MovieViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    fun setData(data: List<Movie>) {
        movies = data
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            Picasso.get()
                .load(BASE_IMAGE_SMALL + movie.poster_path)
                .placeholder(R.drawable.ic_broken_image)
                .into(itemView.movieImage);
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }
    }

}*/
