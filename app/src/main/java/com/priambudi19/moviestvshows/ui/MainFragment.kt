package com.priambudi19.moviestvshows.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.databinding.FragmentMainBinding
import com.priambudi19.moviestvshows.ui.adapter.ViewPagerAdapter

class MainFragment : Fragment() {
    private var _binding:FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this.requireActivity())
        val viewPager2 = binding.viewPager
        viewPager2.adapter = viewPagerAdapter
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                ViewPagerAdapter.MOVIES -> tab.text = getString(R.string.movies_tab)
                ViewPagerAdapter.TV_SHOWS -> tab.text = getString(R.string.tv_show_tab)
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}