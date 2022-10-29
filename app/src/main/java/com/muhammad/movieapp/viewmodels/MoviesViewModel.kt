package com.muhammad.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.movieapp.models.MovieDetails
import com.muhammad.movieapp.models.MoviesResponse
import com.muhammad.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    val searchQuery = MutableLiveData<String>()


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
}