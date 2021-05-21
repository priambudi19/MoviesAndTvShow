package com.priambudi19.moviestvshows.data.model.tvshow


import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("page")
    var page: Int=1,
    @SerializedName("results")
    var results: List<TvShow> = listOf(),
    @SerializedName("total_pages")
    var totalPages: Int =0,
    @SerializedName("total_results")
    var totalResults: Int =0
)