package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.priambudi19.moviestvshows.data.repository.TvShowRepository

class FavoriteTvShowViewModel(private val repo: TvShowRepository) : ViewModel() {
    fun getFavoriteTvShow() = liveData {
        emitSource(repo.getFavoriteTvShow().cachedIn(viewModelScope))
    }
}