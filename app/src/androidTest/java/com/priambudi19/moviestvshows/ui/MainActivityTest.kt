package com.priambudi19.moviestvshows.ui

import android.content.Context
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.utils.EspressoIdlingResource
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainActivityTest {
    private lateinit var instrumentalContext: Context

    @Before
    fun setUp() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadPopularMovies() {
        onView(withId(R.id.chip_popular)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_popular)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTopRatedMovies() {
        onView(withId(R.id.chip_top_rated)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_top_rated)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))


    }

    @Test
    fun loadPopularTV() {
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withId(R.id.chip_popular_tv)).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_tvshow))
            .check(matches(isDisplayed()))
    }

    @Test
    fun loadTopRatedTV() {
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withId(R.id.chip_top_rated_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.chip_top_rated_tv)).perform(click())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rv_movie)).check(matches(isCompletelyDisplayed())).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1),
            click()
        )
        onView(withId(R.id.img_cover_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail_tvhow)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tv_type)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.score_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score_value)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))


    }

    @Test
    fun loadTvShowDetail() {
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withId(R.id.rv_tvshow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvshow)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1),
            click()
        )
        onView(withId(R.id.tv_type)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.score_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_share)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_detail_tvhow)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tv_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_score_value)).check(matches(isDisplayed()))
        onView(withId(R.id.img_cover_detail))
            .check(matches(isDisplayed()))


    }

    @Test
    fun loadFavoriteMovie() {
        onView(withId(R.id.btn_favorite_toolbar)).perform(click())
        onView(withId(R.id.viewPagerFav)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun loadFavoriteTvShow() {
        onView(withId(R.id.btn_favorite_toolbar)).perform(click())
        onView(withId(R.id.viewPagerFav)).check(matches(isCompletelyDisplayed()))
            .perform(swipeLeft())
    }


    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}