package com.muhammad.movieapp.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.muhammad.movieapp.BuildConfig.API_KEY
import com.muhammad.movieapp.api.ApiServices
import com.muhammad.movieapp.database.MoviesDatabase
import com.muhammad.movieapp.helpers.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val url = chain
                .request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            chain.proceed(chain.request().newBuilder().url(url).build())
        }.build()

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitInstance(BASE_URL: String): ApiServices =
        Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context.applicationContext,
        MoviesDatabase::class.java,
        "movies_database"
    ).build()

    @Provides
    @Singleton
    fun provideMoviesDatabaseDao(moviesDatabase: MoviesDatabase) =
        moviesDatabase.moviesDatabaseDao()
}