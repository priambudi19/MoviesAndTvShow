package com.priambudi19.moviestvshows.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class TvShowPagingSource(
    private val remoteDataSource: RemoteDataSource,
    private val tvShowCategory: TvShowCategory
) : PagingSource<Int, TvShow>() {
    private val firstPage = 1
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val page = params.key ?: firstPage
        return try {
            val response: List<TvShow> = when (tvShowCategory) {
                TvShowCategory.POPULAR -> {
                    remoteDataSource.getPopularTvShow(page)
                }
                TvShowCategory.TOP_RATED -> {
                    remoteDataSource.getTopRatedTvShow(page)
                }
            }
            LoadResult.Page(
                response,
                prevKey = if (page == firstPage) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}