package com.priambudi19.moviestvshows.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.repository.local.LocalDataSource
import com.priambudi19.moviestvshows.data.repository.remote.RemoteDataSource
import com.priambudi19.moviestvshows.utils.DataDummy
import com.priambudi19.moviestvshows.utils.LiveDataTestUtil
import com.priambudi19.moviestvshows.utils.PagingSourceTvShowTestUtil
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TvShowRepositoryTest {
    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var localDataSource: LocalDataSource

    @MockK(relaxed = true, relaxUnitFun = true)
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var tvShowRepository: TvShowRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvShowRepository = TvShowRepository(remoteDataSource, localDataSource)

    }
    @Test
    fun getPopularTvShow() {
        every { remoteDataSource.getPopularTvShow(any()) }.returns(DataDummy.getPopularTvShow().results)
            remoteDataSource.getPopularTvShow(1)
            val data = LiveDataTestUtil.getValue(tvShowRepository.getPopularTvShow())
            assertNotNull(data)
            verify { remoteDataSource.getPopularTvShow(any()) }
    }

    @Test
    fun getTopRatedTvShow() {
        every { remoteDataSource.getTopRatedTvShow(any()) }.returns(DataDummy.getTopRatedTvShow().results)
        remoteDataSource.getTopRatedTvShow(1)
        val data = LiveDataTestUtil.getValue(tvShowRepository.getTopRatedTvShow())
        assertNotNull(data)
        verify { remoteDataSource.getTopRatedTvShow(any()) }
    }

    @Test
    fun getDetailTvShow() {
        val tvShowDetail = DataDummy.getDummyListTvShowDetail()[0]
        coEvery { remoteDataSource.getDetailTvShow(any()) }.returns(
            tvShowDetail
        )
        val data = LiveDataTestUtil.getValue(tvShowRepository.getDetailTvShow(tvShowDetail.id))
        assertNotNull(data)
    }


    @Test
    fun getFavoriteTvShow() {
        val pagingSource = PagingSourceTvShowTestUtil() as PagingSource<Int, TvShowDetail>
        every { localDataSource.getListFavoriteTvShow() }.returns(pagingSource)
        val result = tvShowRepository.getFavoriteTvShow()
        assertNotNull(result)
    }

    @Test
    fun deleteFavoriteTvShow() {
        val favTvShow = DataDummy.getDummyListTvShowDetail()[0]
        every { localDataSource.deleteFavoriteTvShow(any()) }.returns(Unit)
        tvShowRepository.deleteFavoriteTvShow(favTvShow)
        verify { localDataSource.deleteFavoriteTvShow(any()) }
    }

    @Test
    fun checkFavoriteTvShow() {
        every { localDataSource.checkFavoriteTvShow(any()) }.returns(1)
        val result = tvShowRepository.checkFavoriteTvShow(DataDummy.getDummyListTvShowDetail()[0].id)
        assertNotNull(result)
        verify { localDataSource.checkFavoriteTvShow(any()) }
    }

    @Test
    fun insertFavoriteTvShow() {
        val favTvShow = DataDummy.getDummyListTvShowDetail()[0]
        every { localDataSource.insertFavoriteTvShow(any()) }.returns(Unit)
        tvShowRepository.addFavoriteTvShow(favTvShow)
        verify { localDataSource.insertFavoriteTvShow(any()) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}