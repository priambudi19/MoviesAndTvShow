package com.priambudi19.moviestvshows.data.model.movie


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_movie")
data class Movie(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String = "",
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String = "",
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String = "",
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,

    )