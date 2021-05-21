package com.priambudi19.moviestvshows.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.databinding.FragmentFavoriteBinding
import com.priambudi19.moviestvshows.ui.adapter.FavoriteViewPagerAdapter


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupViewPager()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setupToolbarDefault() {
        val title: TextView = requireActivity().findViewById(R.id.toolbar_title)
        val back: ImageButton = requireActivity().findViewById(R.id.toolbar_back)
        back.visibility = View.GONE
        title.text = getString(R.string.app_name)

    }
    override fun onStop() {
        super.onStop()
        setupToolbarDefault()
    }

    private fun setupToolbar(){
        val back: ImageButton = requireActivity().findViewById(R.id.toolbar_back)
        val title = requireActivity().findViewById<TextView>(R.id.toolbar_title)
        title.text = getString(R.string.favorite_title)
        back.visibility = View.VISIBLE
    }
    private fun setupViewPager() {
        val viewPagerAdapter = FavoriteViewPagerAdapter(this.requireActivity())
        val viewPager2 = binding.viewPagerFav
        viewPager2.adapter = viewPagerAdapter
        val tabLayout: TabLayout = binding.tabLayoutFav
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                FavoriteViewPagerAdapter.MOVIES -> tab.text = getString(R.string.movies_tab)
                FavoriteViewPagerAdapter.TV_SHOWS -> tab.text = getString(R.string.tv_show_tab)
            }
        }.attach()
    }

}