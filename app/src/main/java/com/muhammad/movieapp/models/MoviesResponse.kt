package com.muhammad.movieapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class MoviesResponse(
    val page: Int = 0,
    val results: List<Movie>? = null,
    @SerializedName("total_pages")
    val totalPages: Int = 0
)


@Entity("movie_table")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster")
    @SerializedName("poster_path")
    val poster: String,
    @ColumnInfo(name = "overview")
    val overview: String? = "",
    @ColumnInfo(name = "voteAverage")
    @SerializedName("vote_average")
    val voteAverage: String,
    @ColumnInfo(name = "voteCount")
    val voteCount: String? = "",
    @ColumnInfo(name = "genres")
    val genres: List<Genre> = listOf()
)

data class Genre(
    val id: Int,
    val name: String
)