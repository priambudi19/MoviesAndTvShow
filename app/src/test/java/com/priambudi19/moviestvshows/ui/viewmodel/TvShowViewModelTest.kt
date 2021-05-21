package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.repository.TvShowRepository
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
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

class TvShowViewModelTest {
    @MockK
    private lateinit var tvShowRepository: TvShowRepository
    private lateinit var tvShowViewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowViewModel = TvShowViewModel(tvShowRepository)
    }

    @Test
    fun getPopularTvShow() {
        val dummy = MutableLiveData<PagingData<TvShow>>()
        dummy.value = PagingData.from(DataDummy.getPopularTvShow().results)
        every {
            tvShowRepository.getPopularTvShow()
        }.returns(dummy)
        val result = tvShowViewModel.getPopularTvShow()
        val data = LiveDataTestUtil.getValue(result)
        assertNotNull(result)
        assertNotNull(data)
        verify { tvShowRepository.getPopularTvShow() }
    }

    @Test
    fun getTopRatedTvShow() {
        val dummy = MutableLiveData<PagingData<TvShow>>()
        dummy.value = PagingData.from(DataDummy.getTopRatedTvShow().results)
        every {
            tvShowRepository.getTopRatedTvShow()
        }.returns(dummy)
        val result = tvShowViewModel.getTopRatedTvShow()
        val data = LiveDataTestUtil.getValue(result)
        assertNotNull(result)
        assertNotNull(data)
        verify { tvShowRepository.getTopRatedTvShow() }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }


}