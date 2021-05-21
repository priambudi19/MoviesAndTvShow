package com.priambudi19.moviestvshows.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.repository.local.LocalDataSource
import com.priambudi19.moviestvshows.data.repository.paging.TvShowCategory
import com.priambudi19.moviestvshows.data.repository.paging.TvShowPagingSource
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import com.priambudi19.moviestvshows.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.UnknownHostException

class TvShowRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getPopularTvShow(): LiveData<PagingData<TvShow>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowPagingSource(
                    remoteDataSource,
                    TvShowCategory.POPULAR
                )
            }).liveData
    }

    fun getTopRatedTvShow(): LiveData<PagingData<TvShow>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                TvShowPagingSource(
                    remoteDataSource,
                    TvShowCategory.TOP_RATED
                )
            }).liveData
    }

    fun getDetailTvShow(id: Int): LiveData<Resource<TvShowDetail>> {
        val data = MutableLiveData<Resource<TvShowDetail>>()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                data.postValue(Resource.Loading())
                val remote = remoteDataSource.getDetailTvShow(id)
                data.postValue(Resource.Success(remote))

            } catch (e: UnknownHostException) {
                data.postValue(Resource.Failure(e.message))
            }
        }
        return data
    }


    fun addFavoriteTvShow(data: TvShowDetail) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                localDataSource.insertFavoriteTvShow(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    fun getFavoriteTvShow(): LiveData<PagingData<TvShowDetail>> {
            return  Pager(
                    config = pagingConfigFavorite,
                    pagingSourceFactory = {
                        localDataSource.getListFavoriteTvShow()
                    }).liveData

        }

    fun deleteFavoriteTvShow(tvShow: TvShowDetail){
        CoroutineScope(Dispatchers.IO).launch {
            localDataSource.deleteFavoriteTvShow(tvShow)
        }

    }

    fun checkFavoriteTvShow(id: Int) : LiveData<Int> {
        val check = MutableLiveData<Int>()
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val data = localDataSource.checkFavoriteTvShow(id)
                check.postValue(data)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return check
    }




    companion object {
        val pagingConfig =
            PagingConfig(1, maxSize = PagingConfig.MAX_SIZE_UNBOUNDED, enablePlaceholders = false)
        val pagingConfigFavorite =
            PagingConfig(1, maxSize = PagingConfig.MAX_SIZE_UNBOUNDED, enablePlaceholders = false)
    }
}