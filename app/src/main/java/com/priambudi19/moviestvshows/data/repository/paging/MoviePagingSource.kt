package com.priambudi19.moviestvshows.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(private val remoteDataSource: RemoteDataSource, private val movieCategory: MovieCategory) : PagingSource<Int, Movie>() {
    private val firstPage = 1
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: firstPage
        return try {
            val response : List<Movie> = when(movieCategory){
                MovieCategory.POPULAR -> {
                    remoteDataSource.getPopularMovie(page)
                }
                MovieCategory.TOP_RATED->{
                    remoteDataSource.getTopRatedMovie(page)
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