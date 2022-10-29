package com.muhammad.movieapp.models

import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    val page: Int = 0,
    val results: List<Movie>? = null,
    @SerializedName("total_pages")
    val totalPages: Int = 0
)


data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("vote_average")
    val voteAverage: String,
)