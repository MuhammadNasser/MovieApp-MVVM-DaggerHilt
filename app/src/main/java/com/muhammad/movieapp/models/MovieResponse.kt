package com.muhammad.movieapp.models

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)


data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("vote_average")
    val voteAverage: String,
)