package com.priambudi19.moviestvshows.di

import androidx.room.Room
import coil.ImageLoader
import coil.util.CoilUtils
import com.priambudi19.moviestvshows.data.repository.MovieRepository
import com.priambudi19.moviestvshows.data.repository.TvShowRepository
import com.priambudi19.moviestvshows.data.repository.local.FavoriteDatabase
import com.priambudi19.moviestvshows.data.repository.local.LocalDataSource
import com.priambudi19.moviestvshows.data.repository.remote.ApiClient
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import com.priambudi19.moviestvshows.ui.viewmodel.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.File

val appModule = module(override = true) {
    single {
        ApiClient().getInstance()
    }
    single {
        RemoteDataSource(get())
    }
    single {
        LocalDataSource(get())
    }
    single {
        MovieRepository(get(), get())
    }
    single {
        TvShowRepository(get(), get())
    }

    single {
        get<FavoriteDatabase>().favoriteDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(), FavoriteDatabase::class.java,
            FavoriteDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        LocalDataSource(get())
    }

    viewModel {
        MovieViewModel(get())
    }
    viewModel {
        DetailMovieViewModel(get())
    }
    viewModel {
        DetailTvShowViewModel(get())
    }
    viewModel {
        TvShowViewModel(get())
    }
    viewModel {
        FavoriteMovieViewModel(get())
    }
    viewModel {
        FavoriteTvShowViewModel(get())
    }

    single {
        Cache(
            directory = File(androidContext().cacheDir, "http_cache"),
            maxSize = 50L * 1024L * 1024L
        )
    }

    single {
        ImageLoader.Builder(androidContext())
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(androidContext()))
                    .build()
            }.build()
    }

}

