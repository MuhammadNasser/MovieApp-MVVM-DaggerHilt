package com.muhammad.movieapp.helpers

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("loadUrl")
fun bindImageView(imageView: ImageView, imageUrl: String?) {
    val context = imageView.context
    Picasso.with(context).load("${Constants.IMAGE_BASE_URL}$imageUrl").fit().into(imageView)
}