package com.muhammad.movieapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.muhammad.movieapp.databinding.ActivityDetailsBinding
import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val moviesViewModel: MoviesViewModel by viewModels()
    private var movieDetails: Movie? = null
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 0)

        with(moviesViewModel) {
            if (movieId > 0) {
                getMovieDetails(movieId)
            }

            checkIsAddedToFavorite(movieId)

            isFavorite.observe(this@DetailsActivity) {
                finishLoading()
                this@DetailsActivity.isFavorite = it
                binding.isFavorite = it
            }

            movieDetails.observe(this@DetailsActivity) {
                finishLoading()
                this@DetailsActivity.movieDetails = it
                binding.movie = it
            }
        }

        with(binding) {
            imageViewBack.setOnClickListener {
                finish()
            }

            addToFavorite.setOnClickListener {
                if (this@DetailsActivity.isFavorite) {
                    movieDetails?.let {
                        moviesViewModel.removeFromFavorite(it.id)
                    }
                } else {
                    addToFavorite.setOnClickListener {
                        movieDetails?.let {
                            moviesViewModel.addToFavorite(it)
                        }
                    }
                }
            }
        }
    }
}