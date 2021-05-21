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
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.databinding.FragmentMovieFavoriteBinding
import com.priambudi19.moviestvshows.ui.adapter.MovieFavoriteAdapter
import com.priambudi19.moviestvshows.ui.adapter.PagingLoadStateAdapter
import com.priambudi19.moviestvshows.ui.movie.DetailMovieFragment
import com.priambudi19.moviestvshows.ui.viewmodel.FavoriteMovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieFavoriteFragment : Fragment() {
    private val viewModel: FavoriteMovieViewModel by sharedViewModel()
    private var _binding: FragmentMovieFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteMovie().observe(viewLifecycleOwner, {
            val mAdapter = MovieFavoriteAdapter()
            mAdapter.setOnItemClickCallback(object : MovieFavoriteAdapter.OnItemClickCallback{
                override fun onItemClicked(data: MovieDetail) {
                    parentFragmentManager.commit {
                        val bundle = bundleOf("id" to data.id)
                        replace(
                            R.id.main_containter,
                            DetailMovieFragment::class.java,
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
            binding.rvMovie.apply {
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