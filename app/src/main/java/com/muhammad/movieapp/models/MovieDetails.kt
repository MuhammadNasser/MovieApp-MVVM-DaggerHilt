package com.muhammad.movieapp.models

import com.google.gson.annotations.SerializedName


data class MovieDetails(
    val id: Int,
    @SerializedName("poster_path")
    val poster: String,
    val title: String,
    val overview: String,
    @SerializedName("vote_average")
    val voteAverage: String,
    @SerializedName("vote_count")
    val voteCount: String,
    val genres: List<Genres>
)

data class Genres(
    val id: Int,
    val name: String
)
