package com.muhammad.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.models.MoviesResponse
import com.muhammad.movieapp.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {

    private val _nowPlayingMovies = MutableLiveData<MoviesResponse>()
    var nowPlayingMoviesPage = 1
    val nowPlayingMovies: LiveData<MoviesResponse>
        get() = _nowPlayingMovies

    private val _topRatedMovies = MutableLiveData<MoviesResponse>()
    var topRatedMoviesPage = 1
    val topRatedMovies: LiveData<MoviesResponse>
        get() = _topRatedMovies

    private val _searchResult = MutableLiveData<MoviesResponse>()
    var searchResultPage = 1
    val searchResult: LiveData<MoviesResponse>
        get() = _searchResult

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie>
        get() = _movieDetails

    private val _favorites = MutableLiveData<MoviesResponse>()
    val favorites: LiveData<MoviesResponse>
        get() = _favorites

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading


    fun getNowPlayingMovies() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getNowPlayingMovies(nowPlayingMoviesPage)
            _nowPlayingMovies.value = handleNowPlayingMoviesResponse(response)
        }
    }


    fun getMovieDetails(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getMovieDetails(movieId)
            if (response.isSuccessful) {
                _movieDetails.value = response.body()
            }
        }
    }

    fun getTopRatedMovies() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getTopRatedMovies(topRatedMoviesPage)
            _topRatedMovies.value = handleTopRatedMoviesResponse(response)
        }
    }

    fun searchMovies(searchQuery: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.searchMovies(searchQuery, searchResultPage)
            _searchResult.value = handleSearchResultResponse(response)
        }
    }

    fun getAllFavorites() {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getAllFavorites().collect {
                _favorites.value = MoviesResponse(results = it.toMutableList())
            }
        }
    }

    fun addToFavorite(movie: Movie) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.insertMovie(movie)
            Timber.d("Movie: ${movie.title} added to favorites successfully")
            checkIsAddedToFavorite(movie.id)
        }
    }

    fun removeFromFavorite(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.deleteMovie(movieId)
            Timber.d("MovieId: $movieId removed from favorites successfully")
            checkIsAddedToFavorite(movieId)
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    fun checkIsAddedToFavorite(movieId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getMovieBy(movieId).collect { movie ->
                _isFavorite.value = movie != null
            }
        }
    }

    fun finishLoading() {
        _isLoading.value = false
    }

    private fun handleNowPlayingMoviesResponse(response: Response<MoviesResponse>): MoviesResponse? {

        if (response.isSuccessful) {
            response.body()?.let {
                nowPlayingMoviesPage++
                if (_nowPlayingMovies.value == null) {
                    return it
                } else {
                    val oldMovies = nowPlayingMovies.value?.results
                    val newMovies = it.results

                    newMovies?.let { newList ->
                        oldMovies?.addAll(newList)
                    }
                    return MoviesResponse(nowPlayingMoviesPage, oldMovies, it.totalPages)
                }
            }
            return null
        } else {
            return null
        }
    }

    private fun handleTopRatedMoviesResponse(response: Response<MoviesResponse>): MoviesResponse? {

        if (response.isSuccessful) {
            response.body()?.let {
                topRatedMoviesPage++
                if (_topRatedMovies.value == null) {
                    return it
                } else {
                    val oldMovies = topRatedMovies.value?.results
                    val newMovies = it.results

                    newMovies?.let { newList ->
                        oldMovies?.addAll(newList)
                    }
                    return MoviesResponse(topRatedMoviesPage, oldMovies, it.totalPages)
                }
            }
            return null
        } else {
            return null
        }
    }

    private fun handleSearchResultResponse(response: Response<MoviesResponse>): MoviesResponse? {

        if (response.isSuccessful) {
            response.body()?.let {
                searchResultPage++
                if (_searchResult.value == null) {
                    return it
                } else {
                    val oldMovies = searchResult.value?.results
                    val newMovies = it.results

                    newMovies?.let { newList ->
                        oldMovies?.addAll(newList)
                    }
                    return MoviesResponse(searchResultPage, oldMovies, it.totalPages)
                }
            }
            return null
        } else {
            return null
        }
    }
}