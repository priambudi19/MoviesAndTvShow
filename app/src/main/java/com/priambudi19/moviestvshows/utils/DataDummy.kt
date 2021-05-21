package com.priambudi19.moviestvshows.utils

import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.data.model.movie.MovieResponse
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowResponse

object DataDummy {
    fun getPopularMovieResponse(): MovieResponse =
        MovieResponse(
            page = 1, movies = listOf(
                Movie(
                    id = 460465,
                    title = "Mortal Kombat",
                    posterPath = "/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg",
                    releaseDate = "2021-04-07",
                    overview = "Washed-up MMA fighter Cole Young, unaware of his heritage, and hunted by Emperor Shang Tsung's best warrior, Sub-Zero, seeks out and trains with Earth's greatest champions as he prepares to stand against the enemies of Outworld in a high stakes battle for the universe.",
                    voteAverage = 7.9

                ),
                Movie(
                    id = 399566,
                    title = "Godzilla vs. Kong",
                    posterPath = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                    releaseDate = "22021-03-24",
                    overview = "In a time when monsters walk the Earth, humanity’s fight for its future sets Godzilla and Kong on a collision course that will see the two most powerful forces of nature on the planet collide in a spectacular battle for the ages.",
                    voteAverage = 8.2

                ),
                Movie(
                    id = 615457,
                    title = "Nobody",
                    posterPath = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
                    releaseDate = "2021-03-18",
                    overview = "Hutch Mansell, a suburban dad, overlooked husband, nothing neighbor — a When two thieves break into his home one night, Hutch's unknown long-simmering rage is ignited and propels him on a brutal path that will uncover dark secrets he fought to leave behind.",
                    voteAverage = 7.5
                ),

                ), totalPages = 1, totalResults = 3
        )

    fun getTopRatedResponse(): MovieResponse =
        MovieResponse(
            page = 1, movies = listOf(
                Movie(
                    id = 724089,
                    title = "Dilwale Dulhania Le Jayenge",
                    posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                    releaseDate = "1994-09-23",
                    overview = """
                        Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga."
                    """.trimIndent(),
                    voteAverage = 8.7

                ),
                Movie(
                    id = 761053,
                    title = "Gabriel's Inferno Part III",
                    posterPath = "/pgqgaUx1cJb5oZQQ5v0tNARCeBp.jpg",
                    releaseDate = "2020-11-19",
                    overview = "The final part of the film adaption of the erotic romance novel Gabriel's Inferno written by an anonymous Canadian author under the pen name Sylvain Reynard.",
                    voteAverage = 8.6

                ),
                Movie(
                    id = 615457,
                    title = "Schindler's List",
                    posterPath = "/oBgWY00bEFeZ9N25wWVyuQddbAo.jpg",
                    releaseDate = "1993-11-30",
                    overview = "The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II",
                    voteAverage = 8.6
                ),

                ), totalPages = 1, totalResults = 3
        )


    fun getPopularTvShow(): TvShowResponse = TvShowResponse(
        page = 1, totalResults = 2, totalPages = 1, results = listOf(
            TvShow(
                id = 88396,
                firstAirDate = "2021-03-19",
                name = "The Falcon and the Winter Soldier",
                overview = "Following the events of “Avengers: Endgame”, the Falcon, Sam Wilson and the Winter Soldier, Bucky Barnes team up in a global adventure that tests their abilities, and their patience.",
                voteAverage = 7.9,

                ),
            TvShow(
                id = 95557,
                firstAirDate = "2021-03-26",
                name = "Invincible",
                overview = "Mark Grayson is a normal teenager except for the fact that his father is the most powerful superhero on the planet. Shortly after his seventeenth birthday, Mark begins to develop powers of his own and enters into his father’s tutelage.",
                voteAverage = 8.9,
            )
        )
    )

    fun getTopRatedTvShow(): TvShowResponse = TvShowResponse(
        page = 1, totalResults = 2, totalPages = 1, results = listOf(
            TvShow(
                id = 100,
                firstAirDate = "2004-05-10",
                name = "I Am Not an Animal",
                overview = "I Am Not An Animal is an animated comedy series about the only six talking animals in the world, whose cosseted existence in a vivisection unit is turned upside down when they are liberated by animal rights activists."
            ),
            TvShow(
                id = 83095,
                firstAirDate = "2019-01-09",
                name = "The Rising of the Shield Hero",
                overview = "Iwatani Naofumi was summoned into a parallel world along with 3 other people to become the world's Heroes. Each of the heroes respectively equipped with their own legendary equipment when summoned, Naofumi received the Legendary Shield as his weapon. Due to Naofumi's lack of charisma and experience he's labeled as the weakest, only to end up betrayed, falsely accused, and robbed by on the third day of adventure. Shunned by everyone from the king to peasants, Naofumi's thoughts were filled with nothing but vengeance and hatred. Thus, his destiny in a parallel World begins..."
            )
        )
    )

    fun getDummyListMovieDetail(): List<MovieDetail> {
        val data = arrayListOf<MovieDetail>()
        getPopularMovieResponse().movies.map {
            data.add(
                MovieDetail(
                    title = it.title,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    voteAverage = it.voteAverage
                )
            )
        }
        return data
    }
    fun getDummyListTvShowDetail(): List<TvShowDetail> {
        val data = arrayListOf<TvShowDetail>()
        getPopularTvShow().results.map {
            data.add(
                TvShowDetail(
                    name = it.name,
                    overview = it.overview,
                    posterPath = it.posterPath,
                    voteAverage = it.voteAverage
                )
            )
        }
        return data
    }
}