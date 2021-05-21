package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.repository.MovieRepository
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
import com.priambudi19.moviestvshows.vo.Resource
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class DetailMovieViewModelTest {
    @MockK
    private lateinit var movieRepository: MovieRepository
    private lateinit var detailMovieViewModel: DetailMovieViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailMovieViewModel = DetailMovieViewModel(movieRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummy = DataDummy.getDummyListMovieDetail()[0]
        val liveData = MutableLiveData<Resource<MovieDetail>>()
        liveData.value = Resource.Success(dummy)
        every { movieRepository.getDetailMovie(any()) }.returns(liveData)
        val result = detailMovieViewModel.getDetailMovie(dummy.id)
        assertNotNull(result)
    }


    @Test
    fun checkFavoriteMovie() {
        val dummyId = liveData { emit(38) }
        every { movieRepository.checkFavoriteMovie(any()) }.returns(dummyId)
        val result = LiveDataTestUtil.getValue(detailMovieViewModel.checkFavoriteMovie(38))
        assertNotNull(result)
    }

    @Test
    fun addFavoriteMovie() {
        val dummy = DataDummy.getDummyListMovieDetail()[0]
        every { movieRepository.addFavoriteMovie(any()) }.returns(Unit)
        detailMovieViewModel.addFavoriteMovie(dummy)
        verify { movieRepository.addFavoriteMovie(any()) }
    }

    @Test
    fun deleteFavoriteMovie() {
        val dummyId = DataDummy.getDummyListMovieDetail()[0]
        every { movieRepository.deleteFavoriteMovie(any()) }.returns(Unit)
        detailMovieViewModel.deleteFavoriteMovie(dummyId)
        verify { movieRepository.deleteFavoriteMovie(any()) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}