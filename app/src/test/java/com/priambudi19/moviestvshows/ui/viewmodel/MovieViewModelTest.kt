package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.repository.MovieRepository
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MovieViewModelTest {
    @MockK
    private lateinit var movieRepository: MovieRepository
    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getPopularMovie() {
        val dummy = MutableLiveData<PagingData<Movie>>()
        dummy.value = PagingData.from(DataDummy.getPopularMovieResponse().movies)
        every {
            movieRepository.getPopularMovies()
        }.returns(dummy)
        val result = movieViewModel.getPopularMovie()
        val data = LiveDataTestUtil.getValue(result)
        assertNotNull(result)
        assertNotNull(data)
        verify { movieRepository.getPopularMovies()}

    }

    @Test
    fun getTopRatedMovie() {
        val dummy = MutableLiveData<PagingData<Movie>>()
        dummy.value = PagingData.from(DataDummy.getTopRatedResponse().movies)
        every {
            movieRepository.getTopRatedMovies()
        }.returns(dummy)
        val result = movieViewModel.getTopRatedMovie()
        val data = LiveDataTestUtil.getValue(result)
        assertNotNull(result)
        assertNotNull(data)
        verify { movieRepository.getTopRatedMovies()}
    }
}