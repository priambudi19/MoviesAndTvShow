package com.priambudi19.moviestvshows.utils

import com.priambudi19.moviestvshows.data.repository.remote.ApiService

object MovieUtil {
    private val months = mapOf(
        1 to "January",
        2 to "February",
        3 to "March",
        4 to "April",
        5 to "May",
        6 to "June",
        7 to "July",
        8 to "August",
        9 to "September",
        10 to "October",
        11 to "November",
        12 to "December",
    )

    fun formatDuration(time: Int): String {
        val hr = time / 60
        val h = hr.toString() + "h"
        val m = (time % 60).toString() + "m"
        return if (hr != 0) {
            "$h $m"
        } else {
            m
        }
    }

    fun formatRelease(release: String?): String {
        return if (release != null) {
            if (release != "") {
                val sq = release.toCharArray()
                val year =
                    (sq[0].toString() + sq[1].toString() + sq[2].toString() + sq[3].toString())
                val month = (sq[5].toString() + sq[6].toString()).toInt()
                val day = (sq[8].toString() + sq[9].toString()).toInt().toString()
                "${months[month]} $day, $year"
            } else {
                ""
            }

        } else {
            ""
        }

    }

    fun getImageUrl(size: Int, posterPath: String?): String {
        return if (posterPath != null) {
            if (posterPath != "") {
                StringBuilder(ApiService.BASE_IMAGE_URL)
                    .append("w")
                    .append(size)
                    .append(posterPath)
                    .toString()
            } else ""
        } else {
            ""
        }

    }


}



