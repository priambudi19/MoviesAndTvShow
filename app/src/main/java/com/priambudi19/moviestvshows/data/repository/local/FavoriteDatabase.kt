package com.priambudi19.moviestvshows.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.utils.GenreConverter


@Database(entities = [MovieDetail::class,TvShowDetail::class], version = 1, exportSchema = false)
@TypeConverters(GenreConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao():FavoriteDao

    companion object{
        const val DATABASE_NAME = "db_favorite"

    }

}