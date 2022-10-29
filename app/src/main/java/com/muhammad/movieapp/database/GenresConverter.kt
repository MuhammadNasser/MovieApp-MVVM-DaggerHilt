package com.muhammad.movieapp.database

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.muhammad.movieapp.models.Genre

class GenresConverter {
    private val gson = GsonBuilder().create()

    @TypeConverter
    fun genresToJsonString(views: List<Genre>?): String? {
        return views.let { gson.toJson(views) }
    }

    @TypeConverter
    fun jsonStringToGenres(json: String): List<Genre>? {
        return json.let {
            gson.fromJson(json, object : TypeToken<ArrayList<Genre>>() {}.type)
        }
    }
}
