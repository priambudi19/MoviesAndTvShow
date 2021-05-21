package com.priambudi19.moviestvshows.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.repository.local.LocalDataSource
import com.priambudi19.moviestvshows.data.repository.paging.MovieCategory
import com.priambudi19.moviestvshows.data.repository.paging.MoviePagingSource
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import com.priambudi19.moviestvshows.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException


class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getPopularMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(
                    remoteDataSource,
                    MovieCategory.POPULAR
                )
            }).liveData
    }

    fun getTopRatedMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                MoviePagingSource(
                    remoteDataSource,
                    MovieCategory.TOP_RATED
                )
            }).liveData
    }

     fun getDetailMovie(id: Int): LiveData<Resource<MovieDetail>> {
        val data = MutableLiveData<Resource<MovieDetail>>()
            try {
                data.postValue(Resource.Loading())
                CoroutineScope(Dispatchers.IO).launch {
                    val remote = remoteDataSource.getDetailMovie(id)
                    data.postValue(Resource.Success(remote))
                }

            } catch (e: UnknownHostException) {
                data.postValue(Resource.Failure(e.message))
            }

        return data
    }

    fun addFavoriteMovie(data: MovieDetail) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                localDataSource.insertFavoriteMovie(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getFavoriteMovie(): LiveData<PagingData<MovieDetail>> {
        return Pager(
            config = pagingConfigFavorite,
            pagingSourceFactory = {
                localDataSource.getListFavoriteMovie()
            }).liveData

    }

    fun checkFavoriteMovie(id: Int) : LiveData<Int> {
        val check = MutableLiveData<Int>()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val data = localDataSource.checkFavoriteMovie(id)
                check.postValue(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return check
    }

    fun deleteFavoriteMovie(movie: MovieDetail){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteFavoriteMovie(movie)
        }

    }

    companion object {
        val pagingConfig =
            PagingConfig(1, maxSize = PagingConfig.MAX_SIZE_UNBOUNDED, enablePlaceholders = false)
        val pagingConfigFavorite =
            PagingConfig(
                pageSize = 4,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                enablePlaceholders = false
            )
    }
}