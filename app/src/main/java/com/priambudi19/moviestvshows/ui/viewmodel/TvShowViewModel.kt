package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.priambudi19.moviestvshows.data.repository.TvShowRepository


class TvShowViewModel(private val repo: TvShowRepository) : ViewModel() {
    fun getPopularTvShow() = liveData {
        emitSource(repo.getPopularTvShow().cachedIn(viewModelScope))
    }

    fun getTopRatedTvShow() = liveData {
        emitSource(repo.getTopRatedTvShow().cachedIn(viewModelScope))
    }


}


