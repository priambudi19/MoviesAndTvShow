package com.priambudi19.moviestvshows.data.repository.local

import androidx.paging.PagingSource
import androidx.room.*
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(data: MovieDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTvShow(data: TvShowDetail)

    @Delete
    fun deleteFavoriteMovie(vararg movie: MovieDetail)

    @Delete
    fun deleteFavoriteTvShow(vararg tvShow: TvShowDetail)

    @Query("SELECT * FROM tb_movie_detail")
    fun getListFavoriteMovie(): PagingSource<Int, MovieDetail>

    @Query("SELECT * FROM tb_tvshow_detail")
    fun getListFavoriteTvShow():  PagingSource<Int, TvShowDetail>

    @Query("SELECT COUNT(id) FROM tb_movie_detail WHERE id = :id")
    fun checkFavoriteMovie(id: Int): Int

    @Query("SELECT COUNT(id) FROM tb_tvshow_detail WHERE id = :id")
    fun checkFavoriteTvShow(id: Int): Int
}