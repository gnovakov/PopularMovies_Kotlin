package com.example.popularmovies_kotlin.ui.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies_kotlin.Const.YOUTUBE_THUMBNAIL_END_URL
import com.example.popularmovies_kotlin.Const.YOUTUBE_THUMBNAIL_START_URL
import com.example.popularmovies_kotlin.R
import com.example.popularmovies_kotlin.api.models.Trailer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.trailer_grid_view_item.view.*

class TrailerAdapter() : ListAdapter<Trailer, TrailerAdapter.TrailerHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_grid_view_item, parent, false)
        Log.d("TAG", "onCreateViewHolder")
        return TrailerHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        val trailer = getItem(position)
        holder.bind(trailer)
    }

    class TrailerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trailer: Trailer) {
            Log.d("TAG", "showTrailers " + trailer.name)
            itemView.trailer_title.text = trailer.name

            val imgUrl = YOUTUBE_THUMBNAIL_START_URL + trailer.key + YOUTUBE_THUMBNAIL_END_URL
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                Picasso.get()
                    .load(imgUri)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                    .into(itemView.trailer_image)
            }

        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<Trailer>() {

        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
            return oldItem == newItem
        }
    }

}