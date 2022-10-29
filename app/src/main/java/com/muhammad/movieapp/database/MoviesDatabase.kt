package com.muhammad.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muhammad.movieapp.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(GenresConverter::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDatabaseDao() : MoviesDatabaseDao

}