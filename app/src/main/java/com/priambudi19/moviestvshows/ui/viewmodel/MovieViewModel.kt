package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.priambudi19.moviestvshows.data.repository.MovieRepository

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun getPopularMovie() = liveData {
        emitSource(repo.getPopularMovies().cachedIn(viewModelScope))
    }

    fun getTopRatedMovie() = liveData {
        emitSource(repo.getTopRatedMovies().cachedIn(viewModelScope))
    }


}