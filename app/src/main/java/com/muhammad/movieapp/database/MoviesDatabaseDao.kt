package com.muhammad.movieapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muhammad.movieapp.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: Movie)

    @Query("SELECT * FROM movie_table")
    fun getAllFavorites(): Flow<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE id = :movieId")
    fun getMovieBy(movieId: Int): Flow<Movie>

    @Query("DELETE FROM movie_table WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)
}
