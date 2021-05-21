package com.priambudi19.moviestvshows.vo

sealed class Resource<T>(var data: T? = null, var message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Failure<T>(message: String?) : Resource<T>(message= message)
    class Loading<T> : Resource<T>()
}