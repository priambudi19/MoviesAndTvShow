package com.priambudi19.moviestvshows.data.model.movie


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.priambudi19.moviestvshows.data.model.genre.Genre

@Entity(tableName = "tb_movie_detail")
data class MovieDetail(
    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    var genres: List<Genre> = emptyList(),
    @ColumnInfo(name = "homepage")
    @SerializedName("homepage")
    var homepage: String ="",
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int =0,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String="",
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String="",
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String="",
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    var runtime: Int=0,
    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    var tagline: String="",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String="",
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0,
)