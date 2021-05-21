package com.priambudi19.moviestvshows.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.repository.TvShowRepository
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

class DetailTvShowViewModelTest {
    @MockK
    private lateinit var tvShowRepository: TvShowRepository
    private lateinit var detailTvShowViewModel: DetailTvShowViewModel


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        detailTvShowViewModel = DetailTvShowViewModel(tvShowRepository)
    }
    @Test
    fun getDetailTvShow() {
        val dummy = DataDummy.getDummyListTvShowDetail()[0]
        val liveData = MutableLiveData<Resource<TvShowDetail>>()
        liveData.value = Resource.Success(dummy)
        every { tvShowRepository.getDetailTvShow(any()) }.returns(liveData)
        val result = detailTvShowViewModel.getDetailTvShow(dummy.id)
        assertNotNull(result)
    }

    @Test
    fun checkFavoriteTvShow() {
        val dummyId = liveData { emit(38) }
        every { tvShowRepository.checkFavoriteTvShow(any()) }.returns(dummyId)
        val result = LiveDataTestUtil.getValue(detailTvShowViewModel.checkFavoriteTvShow(38))
        assertNotNull(result)
    }

    @Test
    fun deleteFavoriteTvShow() {
        val dummyId = DataDummy.getDummyListTvShowDetail()[0]
        every { tvShowRepository.deleteFavoriteTvShow(any()) }.returns(Unit)
        detailTvShowViewModel.deleteFavoriteTvShow(dummyId)
        verify { tvShowRepository.deleteFavoriteTvShow(any()) }
    }

    @Test
    fun addFavoriteTvShow() {
        val dummy = DataDummy.getDummyListTvShowDetail()[0]
        every { tvShowRepository.addFavoriteTvShow(any()) }.returns(Unit)
        detailTvShowViewModel.addFavoriteTvShow(dummy)
        verify { tvShowRepository.addFavoriteTvShow(any()) }
    }
    @After
    fun tearDown(){
        unmockkAll()
    }
}