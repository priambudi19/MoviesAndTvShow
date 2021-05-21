package com.priambudi19.moviestvshows.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import java.io.IOException


class PagingSourceMovieTestUtil : PagingSource<Int, MovieDetail>() {
    private val firstPage = 1
    override fun getRefreshKey(state: PagingState<Int, MovieDetail>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetail> {
        val page = params.key ?: firstPage
        return try {
            val response: List<MovieDetail> = DataDummy.getDummyListMovieDetail()
            LoadResult.Page(
                response,
                prevKey = if (page == firstPage) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }
}

class PagingSourceTvShowTestUtil : PagingSource<Int, TvShowDetail>() {
    private val firstPage = 1
    override fun getRefreshKey(state: PagingState<Int, TvShowDetail>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowDetail> {
        val page = params.key ?: firstPage
        return try {
            val response: List<TvShowDetail> = DataDummy.getDummyListTvShowDetail()
            LoadResult.Page(
                response,
                prevKey = if (page == firstPage) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }
}
