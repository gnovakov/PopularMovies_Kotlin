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
import com.example.popularmovies_kotlin.utils.loadImage
import kotlinx.android.synthetic.main.trailer_grid_view_item.view.*

class TrailerAdapter(private val onClickListener: OnClickListener) : ListAdapter<Trailer, TrailerAdapter.TrailerHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trailer_grid_view_item, parent, false)
        Log.d("TAG", "onCreateViewHolder TRAILER")
        return TrailerHolder(view)
    }

    override fun onBindViewHolder(holder: TrailerHolder, position: Int) {
        val trailer = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(trailer)
        }
        holder.bind(trailer)
    }

    class TrailerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(trailer: Trailer) {
            Log.d("TAG", "showTrailers TRAILER " + trailer.name)
            itemView.trailer_name.text = trailer.name

            val imgUrl = YOUTUBE_THUMBNAIL_START_URL + trailer.key + YOUTUBE_THUMBNAIL_END_URL
            imgUrl.let {
                val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
                itemView.trailer_image.loadImage(imgUri)
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

    class OnClickListener(val clickListener: (trailer: Trailer) -> Unit) {
        fun onClick(trailer: Trailer) = clickListener(trailer)
    }

}