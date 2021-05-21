package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.priambudi19.moviestvshows.data.repository.MovieRepository

class FavoriteMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getFavoriteMovie() = liveData {
        emitSource(movieRepository.getFavoriteMovie().cachedIn(viewModelScope))
    }
}