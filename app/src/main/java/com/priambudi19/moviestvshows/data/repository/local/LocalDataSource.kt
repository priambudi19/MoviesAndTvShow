package com.priambudi19.moviestvshows.data.repository.local

import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail

class LocalDataSource(private val favoriteDao: FavoriteDao){
    fun insertFavoriteMovie(data : MovieDetail) = favoriteDao.insertFavoriteMovie(data)
    fun insertFavoriteTvShow(data : TvShowDetail) = favoriteDao.insertFavoriteTvShow(data)
    fun getListFavoriteMovie() = favoriteDao.getListFavoriteMovie()
    fun getListFavoriteTvShow() = favoriteDao.getListFavoriteTvShow()
    fun checkFavoriteMovie(id: Int) = favoriteDao.checkFavoriteMovie(id)
    fun checkFavoriteTvShow(id: Int) = favoriteDao.checkFavoriteTvShow(id)
    fun deleteFavoriteMovie(movie: MovieDetail) = favoriteDao.deleteFavoriteMovie(movie)
    fun deleteFavoriteTvShow(tvShow: TvShowDetail) = favoriteDao.deleteFavoriteTvShow(tvShow)
}