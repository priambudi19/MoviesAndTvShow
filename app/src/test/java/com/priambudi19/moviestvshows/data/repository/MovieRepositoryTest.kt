package com.priambudi19.moviestvshows.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.repository.local.LocalDataSource
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
import com.priambudi19.moviestvshows.utils.PagingSourceMovieTestUtil
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {
    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var localDataSource: LocalDataSource

    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieRepository = MovieRepository(remoteDataSource, localDataSource)

    }


    @Test
    fun getPopularMovies() {
        every { remoteDataSource.getPopularMovie(any()) }.returns(DataDummy.getPopularMovieResponse().movies)
        remoteDataSource.getPopularMovie(1)
        val data = LiveDataTestUtil.getValue(movieRepository.getPopularMovies())
        assertNotNull(data)
        verify { remoteDataSource.getPopularMovie(any()) }
    }

    @Test
    fun getTopRatedMovies() {
        every { remoteDataSource.getTopRatedMovie(any()) }.returns(DataDummy.getTopRatedResponse().movies)
        remoteDataSource.getTopRatedMovie(1)
        val data = LiveDataTestUtil.getValue(movieRepository.getTopRatedMovies())
        assertNotNull(data)
        verify { remoteDataSource.getTopRatedMovie(any()) }
    }

    @Test
    fun getDetailMovie() {
        val movieDetailDummy = DataDummy.getDummyListMovieDetail()[0]
        coEvery { remoteDataSource.getDetailMovie(any()) }.returns(
            movieDetailDummy
        )
        val data = LiveDataTestUtil.getValue(movieRepository.getDetailMovie(movieDetailDummy.id))
        assertNotNull(data)
    }

    @Test
    fun addFavoriteMovie() {
        val favMovie = DataDummy.getDummyListMovieDetail()[0]
        every { localDataSource.insertFavoriteMovie(any()) }.returns(Unit)
        movieRepository.addFavoriteMovie(favMovie)
        verify { localDataSource.insertFavoriteMovie(any()) }
    }

    @Test
    fun getFavoriteMovie() {
        val pagingSource = PagingSourceMovieTestUtil() as PagingSource<Int, MovieDetail>
        every { localDataSource.getListFavoriteMovie() }.returns(pagingSource)
        val result = movieRepository.getFavoriteMovie()
        assertNotNull(result)
    }

    @Test
    fun checkFavoriteMovie() {
        every { localDataSource.checkFavoriteMovie(any()) }.returns(1)
        val result = movieRepository.checkFavoriteMovie(DataDummy.getDummyListMovieDetail()[0].id)
        assertNotNull(result)

    }

    @Test
    fun deleteFavoriteMovie() {
        val favMovie = DataDummy.getDummyListMovieDetail()[0]
        every { localDataSource.deleteFavoriteMovie(any()) }.returns(Unit)
        movieRepository.deleteFavoriteMovie(favMovie)
        verify { localDataSource.deleteFavoriteMovie(any()) }
    }

    @Test
    fun insertFavoriteMovie() {
        val favMovie = DataDummy.getDummyListMovieDetail()[0]
        every { localDataSource.insertFavoriteMovie(any()) }.returns(Unit)
        movieRepository.addFavoriteMovie(favMovie)
        verify { localDataSource.insertFavoriteMovie(any()) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}