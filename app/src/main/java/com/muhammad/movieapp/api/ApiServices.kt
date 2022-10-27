package com.muhammad.movieapp.api

import com.muhammad.movieapp.models.MovieDetails
import com.muhammad.movieapp.models.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("/movie/now_playing")
    suspend fun getNowPlayingMoviesAsync(): Deferred<MovieResponse>

    @GET("/movie/top_rated")
    suspend fun getTopRatedMoviesAsync(): Deferred<MovieResponse>

    @GET("/movie/{movieId}")
    suspend fun getMovieDetailsAsync(
        @Path("movieId") movieId: Int
    ): Deferred<MovieDetails>

}