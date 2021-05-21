package com.priambudi19.moviestvshows.data.model.tvshow


import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("backdrop_path")
    var backdropPath: String="",
    @SerializedName("first_air_date")
    var firstAirDate: String="",
    @SerializedName("genre_ids")
    var genreIds: List<Int> = listOf(),
    @SerializedName("id")
    var id: Int=0,
    @SerializedName("name")
    var name: String="",
    @SerializedName("overview")
    var overview: String="",
    @SerializedName("poster_path")
    var posterPath: String="",
    @SerializedName("vote_average")
    var voteAverage: Double=0.0,

)