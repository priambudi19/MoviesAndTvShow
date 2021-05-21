package com.priambudi19.moviestvshows.ui.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.data.model.tvshow.TvShow
import com.priambudi19.moviestvshows.data.repository.paging.TvShowCategory
import com.priambudi19.moviestvshows.data.repository.paging.TvShowCategory.POPULAR
import com.priambudi19.moviestvshows.data.repository.paging.TvShowCategory.TOP_RATED
import com.priambudi19.moviestvshows.databinding.FragmentTvShowBinding
import com.priambudi19.moviestvshows.ui.adapter.PagingLoadStateAdapter
import com.priambudi19.moviestvshows.ui.adapter.TvShowAdapter
import com.priambudi19.moviestvshows.ui.viewmodel.TvShowViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowFragment : Fragment() {

    private val viewModel: TvShowViewModel by sharedViewModel()
    private var _binding: FragmentTvShowBinding? = null
    private val binding get() = _binding!!
    private var popularAdapter = TvShowAdapter()
    private var topRatedAdapter = TvShowAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvTvshow.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
        fetchData(POPULAR)
        setupList(popularAdapter)
        setChip()

    }

    private fun fetchData(movieCategory: TvShowCategory) {
        when (movieCategory) {
            TOP_RATED -> {
                topRatedAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: TvShow) {
                        parentFragmentManager.commit {
                            val bundle = bundleOf("id" to data.id)
                            replace(
                                R.id.main_containter,
                                DetailTvShowFragment::class.java,
                                bundle
                            )
                            addToBackStack(null)
                        }
                    }
                })
                viewModel.getTopRatedTvShow().observe(viewLifecycleOwner, {
                    lifecycleScope.launchWhenStarted {
                        binding.progressBar.visibility = View.GONE
                        topRatedAdapter.submitData(it)
                    }
                })
            }

            POPULAR -> {
                popularAdapter.setOnItemClickCallback(object : TvShowAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: TvShow) {
                        parentFragmentManager.commit {
                            val bundle = bundleOf("id" to data.id)
                            replace(
                                R.id.main_containter,
                                DetailTvShowFragment::class.java,
                                bundle
                            )
                            addToBackStack(null)
                        }
                    }
                })
                viewModel.getPopularTvShow().observe(viewLifecycleOwner, {
                    lifecycleScope.launchWhenStarted {
                        binding.progressBar.visibility = View.GONE
                        popularAdapter.submitData(it)
                    }
                })
            }
        }

    }

    private fun setupList(mAdapter: TvShowAdapter) {
        with(binding) {
            rvTvshow.apply {
                adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(mAdapter),
                    footer = PagingLoadStateAdapter(mAdapter)
                )

            }
        }
    }

    private fun setChip() {
        with(binding) {
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.chip_popular_tv -> {
                        fetchData(POPULAR)
                        setupList(popularAdapter)
                    }
                    R.id.chip_top_rated_tv -> {
                        fetchData(TOP_RATED)
                        setupList(topRatedAdapter)
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}