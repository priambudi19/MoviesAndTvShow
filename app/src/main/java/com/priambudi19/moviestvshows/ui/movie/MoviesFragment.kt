package com.priambudi19.moviestvshows.ui.movie

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
import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.data.repository.paging.MovieCategory
import com.priambudi19.moviestvshows.data.repository.paging.MovieCategory.POPULAR
import com.priambudi19.moviestvshows.data.repository.paging.MovieCategory.TOP_RATED
import com.priambudi19.moviestvshows.databinding.FragmentMoviesBinding
import com.priambudi19.moviestvshows.ui.adapter.MovieAdapter
import com.priambudi19.moviestvshows.ui.adapter.PagingLoadStateAdapter
import com.priambudi19.moviestvshows.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment() {
    private val viewModel: MovieViewModel by sharedViewModel()
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private var popularAdapter = MovieAdapter()
    private var topRatedAdapter = MovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }
        fetchData(POPULAR)
        setupList(popularAdapter)
        setChip()


    }

    private fun fetchData(movieCategory: MovieCategory) {
        when (movieCategory) {
            TOP_RATED -> {
                topRatedAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Movie) {
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
                viewModel.getTopRatedMovie().observe(viewLifecycleOwner, {
                    lifecycleScope.launchWhenStarted {
                        topRatedAdapter.submitData(it)
                        binding.progressBar.visibility = View.GONE
                    }
                })

            }
            POPULAR -> {
                popularAdapter.setOnItemClickCallback(object : MovieAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Movie) {
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
                viewModel.getPopularMovie().observe(viewLifecycleOwner, {
                    lifecycleScope.launchWhenStarted {
                        binding.progressBar.visibility = View.GONE
                        popularAdapter.submitData(it)
                    }
                })

            }
        }

    }

    private fun setupList(mAdapter: MovieAdapter) {
        with(binding) {
            rvMovie.apply {
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
                    R.id.chip_popular -> {
                        fetchData(POPULAR)
                        setupList(popularAdapter)
                    }
                    R.id.chip_top_rated -> {
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