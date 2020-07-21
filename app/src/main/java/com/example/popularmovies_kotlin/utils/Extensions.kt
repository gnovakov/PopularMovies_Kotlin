package com.example.popularmovies_kotlin.utils

import android.net.Uri
import android.widget.ImageView
import com.example.popularmovies_kotlin.R
import com.squareup.picasso.Picasso

fun ImageView.loadImage(imgUri: Uri) = Picasso.get()
    .load(imgUri)
    .placeholder(R.drawable.loading_animation)
    .error(R.drawable.ic_broken_image)
    .into(this)