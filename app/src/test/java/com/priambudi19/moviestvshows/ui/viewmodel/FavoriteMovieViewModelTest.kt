package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.repository.MovieRepository
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteMovieViewModelTest {
    @MockK
    private lateinit var movieRepository: MovieRepository
    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favoriteMovieViewModel = FavoriteMovieViewModel(movieRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val dummy = MutableLiveData<PagingData<MovieDetail>>()
        dummy.value = PagingData.from(DataDummy.getDummyListMovieDetail())
        every { movieRepository.getFavoriteMovie() }
            .returns(dummy)
        val data = LiveDataTestUtil.getValue(favoriteMovieViewModel.getFavoriteMovie())
        assertNotNull(data)
    }

    @After
    fun tearDown(){
        unmockkAll()
    }
}