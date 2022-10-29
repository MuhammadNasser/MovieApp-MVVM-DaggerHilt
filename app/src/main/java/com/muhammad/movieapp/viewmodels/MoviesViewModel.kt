package com.muhammad.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.models.MovieDetails
import com.muhammad.movieapp.models.MoviesResponse
import com.muhammad.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<MoviesResponse>()
    val nowPlayingMovies: LiveData<MoviesResponse>
        get() = _nowPlayingMovies

    private val _topRatedMovies = MutableLiveData<MoviesResponse>()
    val topRatedMovies: LiveData<MoviesResponse>
        get() = _topRatedMovies

    private val _searchResult = MutableLiveData<MoviesResponse>()
    val searchResult: LiveData<MoviesResponse>
        get() = _searchResult

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails>
        get() = _movieDetails

    private val _favorites = MutableLiveData<MoviesResponse>()
    val favorites: LiveData<MoviesResponse>
        get() = _favorites

    private var _favorite: Movie? = null


    fun getNowPlayingMovies() {
        viewModelScope.launch {
            val response = repository.getNowPlayingMovies()
            if (response.isSuccessful) {
                _nowPlayingMovies.value = response.body()
            }
        }
    }

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId)
            if (response.isSuccessful) {
                _movieDetails.value = response.body()
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            val response = repository.getTopRatedMovies()
            if (response.isSuccessful) {
                _topRatedMovies.value = response.body()
            }
        }
    }

    fun searchMovies(searchQuery: String) {
        viewModelScope.launch {
            val response = repository.searchMovies(searchQuery)
            if (response.isSuccessful) {
                _searchResult.value = response.body()
            }
        }
    }

    fun getAllFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect {
                _favorites.value = MoviesResponse(results = it)
            }
        }
    }

    private fun getFavoriteMovie(movieId: Int): Movie? {
        viewModelScope.launch {
            repository.getMovieBy(movieId).collect {
                _favorite = it
            }
        }
        return _favorite
    }

    fun addToFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.insertMovie(movie)
            Timber.d("Movie: ${movie.title} added to favorites successfully")
        }
    }

    fun removeFromFavorite(movieId: Int) {
        viewModelScope.launch {
            repository.deleteMovie(movieId)
            Timber.d("MovieId: $movieId removed from favorites successfully")
        }
    }

    fun isAddedToFavorite(movieId: Int): Boolean {
        return getFavoriteMovie(movieId) != null
    }
}