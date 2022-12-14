package com.muhammad.movieapp.api

import com.muhammad.movieapp.models.Movie
import com.muhammad.movieapp.models.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesAsync(
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMoviesAsync(
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetailsAsync(
        @Path("movieId") movieId: Int
    ): Response<Movie>

    @GET("search/movie")
    suspend fun searchMoviesAsync(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>
}