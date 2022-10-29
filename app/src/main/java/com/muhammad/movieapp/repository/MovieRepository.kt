package com.muhammad.movieapp.repository

import com.muhammad.movieapp.api.ApiServices
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val apiServices: ApiServices) {

    suspend fun getNowPlayingMovies() = apiServices.getNowPlayingMoviesAsync()

    suspend fun getTopRatedMovies() = apiServices.getTopRatedMoviesAsync()

    suspend fun getMovieDetails(movieId: Int) = apiServices.getMovieDetailsAsync(movieId)

    suspend fun searchMovies(searchQuery: String) = apiServices.searchMoviesAsync(searchQuery)
}