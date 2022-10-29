package com.muhammad.movieapp.api

import com.muhammad.movieapp.models.MovieDetails
import com.muhammad.movieapp.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesAsync(): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesAsync(): Response<MoviesResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetailsAsync(
        @Path("movieId") movieId: Int
    ): Response<MovieDetails>

    @GET("search/movie")
    suspend fun searchMoviesAsync(
        @Query("query") query: String
    ): Response<MoviesResponse>
}