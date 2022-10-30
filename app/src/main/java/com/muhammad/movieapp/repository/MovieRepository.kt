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

    suspend fun getNowPlayingMovies(page: Int) = apiServices.getNowPlayingMoviesAsync(page)

    suspend fun getTopRatedMovies(page: Int) = apiServices.getTopRatedMoviesAsync(page)

    suspend fun getMovieDetails(movieId: Int) = apiServices.getMovieDetailsAsync(movieId)

    suspend fun searchMovies(searchQuery: String, page: Int) =
        apiServices.searchMoviesAsync(searchQuery, page)

    suspend fun insertMovie(movie: Movie) = moviesDatabaseDao.insertMovie(movie)

    suspend fun deleteMovie(movieId: Int) = moviesDatabaseDao.deleteMovie(movieId)

    fun getAllFavorites() = moviesDatabaseDao.getAllFavorites()

    fun getMovieBy(movieId: Int) = moviesDatabaseDao.getMovieBy(movieId)
}