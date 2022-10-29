package com.muhammad.movieapp.helpers

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.muhammad.movieapp.R
import com.muhammad.movieapp.models.Genre
import com.squareup.picasso.Picasso

@BindingAdapter("loadUrl")
fun bindImageView(imageView: ImageView, imageUrl: String?) {
    val context = imageView.context
    Picasso.with(context).load("${Constants.IMAGE_BASE_URL}$imageUrl").fit().into(imageView)
}

@BindingAdapter("favoriteStatus")
fun bindImageView(imageView: ImageView, isFavorite: Boolean) {
    if (isFavorite) {
        imageView.setImageResource(R.drawable.ic_favorite)
    } else {
        imageView.setImageResource(R.drawable.ic_unfavorite)
    }

}

@SuppressLint("SetTextI18n")
@BindingAdapter("textListOfStrings")
fun bindTextView(textView: TextView, genres: List<Genre>?) {
    genres?.let {
        for (genre in genres) {
            if (genre == genres.last()) {
                textView.append(" ${genre.name}")
            } else {
                textView.append(" ${genre.name} -")
            }
        }
    }
}