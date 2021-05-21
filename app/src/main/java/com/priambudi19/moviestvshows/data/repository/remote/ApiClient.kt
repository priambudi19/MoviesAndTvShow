package com.priambudi19.moviestvshows.data.repository.remote

import com.google.gson.Gson
import com.priambudi19.moviestvshows.BuildConfig
import com.priambudi19.moviestvshows.data.repository.remote.ApiService.Companion.BASE_URL
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient : KoinComponent {
    private val cache : Cache by inject()
    private val logging = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val o = chain.request()
            val url: HttpUrl = o.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY).build()
            val request = o.newBuilder()
                .url(url)
                .method(o.method, o.body)
                .build()

            chain.proceed(request)
        }
        .cache(cache)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()


    fun getInstance(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(client)
        .build()
        .create(ApiService::class.java)
}