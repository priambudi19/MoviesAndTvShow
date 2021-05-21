package com.priambudi19.moviestvshows.data.repository.remote

import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.utils.EspressoIdlingResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemoteDataSource(private val apiService: ApiService) {
     fun getPopularMovie(page: Int): List<Movie> {
        EspressoIdlingResource.increment()
         val response = arrayListOf<Movie>()
             CoroutineScope(Dispatchers.IO).launch {
             response.addAll(apiService.getPopularMovie(page).movies)
         }
        EspressoIdlingResource.decrement()
        return response
    }

     fun getTopRatedMovie(page: Int): List<Movie> {
        EspressoIdlingResource.increment()
         val response = arrayListOf<Movie>()
         CoroutineScope(Dispatchers.IO).launch {
             response.addAll(apiService.getTopRatedMovie(page).movies)
         }
        EspressoIdlingResource.decrement()
        return response
    }

     fun getPopularTvShow(page: Int): List<TvShow> {
        EspressoIdlingResource.increment()
         val response = arrayListOf<TvShow>()
         CoroutineScope(Dispatchers.IO).launch {
             response.addAll(apiService.getPopularTvShow(page).results)
         }
        EspressoIdlingResource.decrement()
        return response
    }

     fun getTopRatedTvShow(page: Int): List<TvShow> {
        EspressoIdlingResource.increment()
         val response = arrayListOf<TvShow>()
         CoroutineScope(Dispatchers.IO).launch {
             response.addAll(apiService.getTopRatedTvShow(page).results)
         }
        EspressoIdlingResource.decrement()
        return response
    }

     suspend fun getDetailMovie(id: Int): MovieDetail {
        EspressoIdlingResource.increment()
         val response = apiService.getDetailMovie(id)
        EspressoIdlingResource.decrement()
        return response
    }

    suspend fun getDetailTvShow(id: Int): TvShowDetail {
        EspressoIdlingResource.increment()
        val response = apiService.getDetailTvShow(id)
        EspressoIdlingResource.decrement()
        return response
    }
}