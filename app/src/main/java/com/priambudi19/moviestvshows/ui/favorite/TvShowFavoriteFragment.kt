package com.priambudi19.moviestvshows.ui.favorite

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
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.databinding.FragmentTvShowFavoriteBinding
import com.priambudi19.moviestvshows.ui.adapter.PagingLoadStateAdapter
import com.priambudi19.moviestvshows.ui.adapter.TvShowFavoriteAdapter
import com.priambudi19.moviestvshows.ui.tvshow.DetailTvShowFragment
import com.priambudi19.moviestvshows.ui.viewmodel.FavoriteTvShowViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TvShowFavoriteFragment : Fragment() {
    private val viewModel: FavoriteTvShowViewModel by sharedViewModel()
    private var _binding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteTvShow().observe(viewLifecycleOwner, {
            val mAdapter = TvShowFavoriteAdapter()
            mAdapter.setOnItemClickCallback(object : TvShowFavoriteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: TvShowDetail) {
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
            lifecycleScope.launchWhenStarted {
                binding.progressBar.visibility = View.GONE
                mAdapter.submitData(it)
            }
            binding.rvTvshowFav.apply {
                layoutManager = LinearLayoutManager(context)
                itemAnimator = null
                adapter = mAdapter.withLoadStateHeaderAndFooter(
                    header = PagingLoadStateAdapter(mAdapter),
                    footer = PagingLoadStateAdapter(mAdapter)
                )
            }
        })
    }


}