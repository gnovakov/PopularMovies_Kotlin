package com.example.popularmovies_kotlin.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.popularmovies_kotlin.api.models.Trailer

//class TrailerAdapter(private val onClickListener: OnClickListener ) : ListAdapter<Trailer, TrailerAdapter.TrailerViewHolder>(DiffCallback) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerAdapter.TrailerViewHolder {
//        return TrailerViewHolder(
//            TrailerGridViewItemBinding.inflate(
//            LayoutInflater.from(parent.context)))
//    }
//
//    override fun onBindViewHolder(holder: TrailerAdapter.TrailerViewHolder, position: Int) {
//        val trailer = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(trailer)
//        }
//        holder.bind(trailer)
//    }
//
//
//    companion object DiffCallback : DiffUtil.ItemCallback<Trailer>() {
//
//        override fun areItemsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: Trailer, newItem: Trailer): Boolean {
//            return oldItem.id == newItem.id
//        }
//    }
//
//    class TrailerViewHolder(private var binding: TrailerGridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
//        fun bind(trailer: Trailer) {
//            binding.trailer = trailer
//            binding.executePendingBindings()
//        }
//    }
//
//    class OnClickListener(val clickListener: (trailer: Trailer) -> Unit) {
//        fun onClick(trailer: Trailer) = clickListener(trailer)
//    }
//
//}