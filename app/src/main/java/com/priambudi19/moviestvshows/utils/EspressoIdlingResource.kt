package com.priambudi19.moviestvshows.utils

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {
    private const val RESOURCE_NAME = "Movie & TV Resource"
    val idlingResource = CountingIdlingResource(RESOURCE_NAME)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}
