package com.muhammad.movieapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muhammad.movieapp.databinding.MovieLayoutAdapterBinding
import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.models.MoviesResponse
import com.muhammad.movieapp.viewmodels.MoviesViewModel

class MoviesAdapter(
    private val moviesViewModel: MoviesViewModel,
    private val onItemClickListener: MovieClickListener
) :
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
            holder.bind(it, onItemClickListener, moviesViewModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(private val binding: MovieLayoutAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            movieItem: Movie,
            onItemClickListener: MovieClickListener,
            moviesViewModel: MoviesViewModel
        ) {
            with(binding) {
                movie = movieItem
                clickListener = onItemClickListener
                viewModel = moviesViewModel
                executePendingBindings()

                addToFavorite.setOnClickListener {
                    if (addToFavorite.isChecked) {
                        moviesViewModel.addToFavorite(movieItem)
                    } else {
                        moviesViewModel.removeFromFavorite(movieItem.id)
                    }
                }
            }
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