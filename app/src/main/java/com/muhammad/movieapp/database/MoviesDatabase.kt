package com.muhammad.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammad.movieapp.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDatabaseDao() : MoviesDatabaseDao

}