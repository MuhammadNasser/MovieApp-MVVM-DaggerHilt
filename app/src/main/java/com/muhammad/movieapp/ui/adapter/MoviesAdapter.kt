package com.muhammad.movieapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammad.movieapp.databinding.MovieLayoutAdapterBinding
import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.models.MoviesResponse

class MoviesAdapter(private val onItemClickListener: MovieClickListener) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    var moviesResponse = MoviesResponse()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        moviesResponse.results?.let {
            return it.size
        }
        return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = moviesResponse.results?.get(position)
        movie?.let {
            holder.bind(it, onItemClickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: MovieLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, onItemClickListener: MovieClickListener) {
            binding.movie = movie
            binding.clickListener = onItemClickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieLayoutAdapterBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class MovieClickListener(val clickListener: (movie: Movie) -> Unit) {
        fun onClick(movie: Movie) = clickListener(movie)
    }
}