package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.repository.TvShowRepository

class DetailTvShowViewModel(private val repo: TvShowRepository) : ViewModel() {
    fun getDetailTvShow(id: Int) = liveData {
        emitSource(repo.getDetailTvShow(id))
    }
    fun addFavoriteTvShow(tvShowDetail: TvShowDetail) {
        repo.addFavoriteTvShow(tvShowDetail)
    }
    fun checkFavoriteTvShow(id: Int) = liveData {
        emitSource(repo.checkFavoriteTvShow(id))
    }

    fun deleteFavoriteTvShow(tvShow: TvShowDetail){
        repo.deleteFavoriteTvShow(tvShow)
    }
}