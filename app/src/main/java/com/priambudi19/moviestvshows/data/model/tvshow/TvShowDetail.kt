package com.priambudi19.moviestvshows.data.model.tvshow


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.priambudi19.moviestvshows.data.model.genre.Genre

@Entity(tableName = "tb_tvshow_detail")
data class TvShowDetail(

    @ColumnInfo(name = "first_air_date")
    @SerializedName("first_air_date")
    var firstAirDate: String = "",

    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    var genres: List<Genre> = emptyList(),

    @ColumnInfo(name = "homepage")
    @SerializedName("homepage")
    var homepage: String = "",

    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String = "",

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String = "",

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String = "",

    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    var tagline: String = "",

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,


    )