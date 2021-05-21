package com.priambudi19.moviestvshows.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.priambudi19.moviestvshows.R
import com.priambudi19.moviestvshows.data.model.movie.MovieDetail
import com.priambudi19.moviestvshows.databinding.FragmentDetailMovieBinding
import com.priambudi19.moviestvshows.ui.viewmodel.DetailMovieViewModel
import com.priambudi19.moviestvshows.utils.MovieUtil
import com.priambudi19.moviestvshows.vo.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailMovieViewModel by sharedViewModel()
    private var id: Int? = null
    private val imageLoader: ImageLoader by inject()
    private var lastFavState: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id", 0)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        setupToolbar()

        return binding.root
    }

    private fun setupToolbar() {
        val title: TextView = requireActivity().findViewById(R.id.toolbar_title)
        val back: ImageButton = requireActivity().findViewById(R.id.toolbar_back)
        back.visibility = View.VISIBLE
        title.text = getString(R.string.detail)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id?.let {
            viewModel.checkFavoriteMovie(it)
                .observe(viewLifecycleOwner, { fav ->
                    when (fav) {
                        0 -> {
                            binding.btnFavorite.isChecked = false
                            lastFavState = false
                        }
                        1 -> {
                            binding.btnFavorite.isChecked = true
                            lastFavState = true
                        }
                    }
                })
            viewModel.getDetailMovie(it).observe(viewLifecycleOwner, { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBarLoading.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        val data = resource.data
                        if (data != null) {
                            setupStateFavorite(data)
                            binding.apply {
                                progressBarLoading.visibility = View.GONE
                                tvTagline.text = data.tagline
                                imgCoverDetail.load(
                                    MovieUtil.getImageUrl(300, data.posterPath),
                                    imageLoader
                                ) {
                                    transformations(RoundedCornersTransformation(16f))
                                }
                                tvTitleDetailTvhow.text = data.title
                                tvOverview.text = data.overview
                                tvReleaseDate.text =
                                    MovieUtil.formatRelease(data.releaseDate)
                                tvDuration.text = MovieUtil.formatDuration(data.runtime)
                                scoreBar.progress = data.voteAverage.roundToInt()
                                tvScoreValue.text = data.voteAverage.toString()
                                tvGenre.text =
                                    data.genres.joinToString { genre -> genre.name }
                                btnShare.setOnClickListener {
                                    val shareItem = StringBuilder(data.title)
                                        .appendLine(data.releaseDate)
                                        .appendLine(data.genres.joinToString { genre -> genre.name })
                                        .appendLine(data.overview)
                                        .appendLine(data.homepage).toString()
                                    val intent = Intent().apply {
                                        this.action = Intent.ACTION_SEND
                                        this.putExtra(Intent.EXTRA_TEXT, shareItem)
                                        this.type = "text/plain"
                                    }
                                    startActivity(intent)
                                }

                            }
                        }

                    }
                    is Resource.Failure -> {
                        binding.progressBarLoading.visibility = View.GONE
                        Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()

                    }
                }
            })
        }
    }

    private fun setupToolbarDefault() {
        val title: TextView = requireActivity().findViewById(R.id.toolbar_title)
        val back: ImageButton = requireActivity().findViewById(R.id.toolbar_back)
        back.visibility = View.GONE
        title.text = getString(R.string.app_name)

    }

    private fun setupStateFavorite(data: MovieDetail) {
        binding.btnFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (!isChecked) {
                button.setOnClickListener {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.confirm_delete))
                        .setMessage(getString(R.string.desc_delete_confirm))
                        .setPositiveButton(
                            getString(R.string.yes_button)
                        ) { _, _ ->
                            viewModel.deleteFavoriteMovie(data)
                            Toast.makeText(
                                requireContext(),
                                data.title + " " + getString(R.string.deleted),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .setNegativeButton(getString(R.string.no_button)) { _, _ ->
                            lastFavState.let { state ->
                                binding.btnFavorite.isChecked = state
                            }
                        }
                        .setOnCancelListener {
                            lastFavState.let { state ->
                                binding.btnFavorite.isChecked = state
                            }
                        }
                        .show()

                }
            } else {
                button.setOnClickListener {
                    viewModel.addFavoriteMovie(data)
                    Toast.makeText(
                        requireContext(),
                        data.title + " " + getString(R.string.added_to_fav),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                lastFavState = true
            }
        }
    }

    override fun onStop() {
        super.onStop()
        setupToolbarDefault()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}


