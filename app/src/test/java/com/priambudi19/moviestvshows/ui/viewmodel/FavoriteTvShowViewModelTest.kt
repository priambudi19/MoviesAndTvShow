package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.repository.TvShowRepository
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

class FavoriteTvShowViewModelTest {
    @MockK
    private lateinit var tvShowRepository: TvShowRepository
    private lateinit var favoriteTvShowViewModel: FavoriteTvShowViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        favoriteTvShowViewModel = FavoriteTvShowViewModel(tvShowRepository)
    }
    @Test
    fun getFavoriteTvShow() {
        val dummy = MutableLiveData<PagingData<TvShowDetail>>()
        dummy.value = PagingData.from(DataDummy.getDummyListTvShowDetail())
        every { tvShowRepository.getFavoriteTvShow() }
            .returns(dummy)
        val data = LiveDataTestUtil.getValue(tvShowRepository.getFavoriteTvShow())
        assertNotNull(data)
    }
    @After
    fun tearDown(){
        unmockkAll()
    }
}