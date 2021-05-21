package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.repository.MovieRepository

class DetailMovieViewModel(private val repo: MovieRepository) : ViewModel() {
    fun getDetailMovie(id: Int) = liveData {
        emitSource(repo.getDetailMovie(id))
    }
    fun addFavoriteMovie(movieDetail: MovieDetail){
        repo.addFavoriteMovie(movieDetail)
    }

    fun checkFavoriteMovie(id: Int) = liveData {
       emitSource(repo.checkFavoriteMovie(id))
    }

    fun deleteFavoriteMovie(movie: MovieDetail){
        repo.deleteFavoriteMovie(movie)
    }
}
