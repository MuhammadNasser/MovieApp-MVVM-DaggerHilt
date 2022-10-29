package com.muhammad.movieapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.muhammad.movieapp.databinding.ActivityDetailsBinding
import com.muhammad.movieapp.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 0)

        if (movieId > 0) {
            moviesViewModel.getMovieDetails(movieId)
        }

        binding.imageViewBack.setOnClickListener {
            finish()
        }

        moviesViewModel.movieDetails.observe(this) {
            binding.movieDetails = it
        }
    }
}