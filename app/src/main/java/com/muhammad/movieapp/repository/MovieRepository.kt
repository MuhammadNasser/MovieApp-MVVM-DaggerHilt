package com.muhammad.movieapp.repository

import com.muhammad.movieapp.api.ApiServices
import com.muhammad.movieapp.database.MoviesDatabaseDao
import com.muhammad.movieapp.models.Movie
import javax.inject.Inject

class MovieRepository
@Inject
constructor(
    private val apiServices: ApiServices,
    private val moviesDatabaseDao: MoviesDatabaseDao
) {

    suspend fun getNowPlayingMovies() = apiServices.getNowPlayingMoviesAsync()

    suspend fun getTopRatedMovies() = apiServices.getTopRatedMoviesAsync()

    suspend fun getMovieDetails(movieId: Int) = apiServices.getMovieDetailsAsync(movieId)

    suspend fun searchMovies(searchQuery: String) = apiServices.searchMoviesAsync(searchQuery)

    suspend fun insertMovie(movie: Movie) = moviesDatabaseDao.insertMovie(movie)

    suspend fun deleteMovie(movieId: Int) = moviesDatabaseDao.deleteMovie(movieId)

    fun getAllFavorites() = moviesDatabaseDao.getAllFavorites()

    fun getMovieBy(movieId: Int) = moviesDatabaseDao.getMovieBy(movieId)
}