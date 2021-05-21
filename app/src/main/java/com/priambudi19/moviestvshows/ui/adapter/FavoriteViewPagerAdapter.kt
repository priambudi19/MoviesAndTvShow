package com.priambudi19.moviestvshows.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.priambudi19.moviestvshows.ui.favorite.MovieFavoriteFragment
import com.priambudi19.moviestvshows.ui.favorite.TvShowFavoriteFragment

class FavoriteViewPagerAdapter(fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                MOVIES -> MovieFavoriteFragment()
                TV_SHOWS -> TvShowFavoriteFragment()

                else -> MovieFavoriteFragment()
            }
        }

        companion object {
            const val MOVIES = 0
            const val TV_SHOWS = 1

        }
}