package com.priambudi19.moviestvshows.data.repository.remote

import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.movie.MovieResponse
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("page") page : Int): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(@Query("page") page : Int): MovieResponse

    @GET("tv/popular")
    suspend fun getPopularTvShow(@Query("page") page : Int): TvShowResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(@Query("page") page : Int): TvShowResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") id: Int
    ): MovieDetail

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") id: Int
    ): TvShowDetail

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"
    }
}