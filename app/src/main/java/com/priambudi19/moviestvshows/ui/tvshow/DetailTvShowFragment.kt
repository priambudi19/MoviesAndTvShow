package com.priambudi19.moviestvshows.ui.tvshow

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
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.databinding.FragmentDetailTvShowBinding
import com.priambudi19.moviestvshows.ui.viewmodel.DetailTvShowViewModel
import com.priambudi19.moviestvshows.utils.MovieUtil
import com.priambudi19.moviestvshows.vo.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.math.roundToInt


class DetailTvShowFragment : Fragment() {
    private var _binding: FragmentDetailTvShowBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailTvShowViewModel by sharedViewModel()
    private var id: Int? = null
    private var lastFavState = false
    private val imageLoader: ImageLoader by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getInt("id", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailTvShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        id?.let {
            viewModel.checkFavoriteTvShow(it)
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
            binding.apply {
                viewModel.getDetailTvShow(it).observe(viewLifecycleOwner, { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.progressBarLoading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            val data = resource.data
                            if (data != null) {
                                setupStateFavorite(data)
                                progressBarLoading.visibility = View.GONE
                                imgCoverDetail.load(
                                    MovieUtil.getImageUrl(200, data.posterPath),
                                    imageLoader
                                ) {
                                    transformations(RoundedCornersTransformation(16f))
                                }
                                tvTitleDetailTvhow.text = data.name
                                tvOverview.text = data.overview
                                tvReleaseDate.text =
                                    MovieUtil.formatRelease(data.firstAirDate)
                                scoreBar.progress = data.voteAverage.roundToInt()
                                tvScoreValue.text = data.voteAverage.toString()
                                tvGenre.text = data.genres
                                    .joinToString { genre -> genre.name }
                                btnShare.setOnClickListener {
                                    val shareItem = StringBuilder()
                                        .appendLine(data.name)
                                        .appendLine(data.firstAirDate)
                                        .appendLine(data.genres.joinToString { genre -> genre.name })
                                        .appendLine()
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
                        is Resource.Failure -> {
                            binding.progressBarLoading.visibility = View.GONE
                            Toast.makeText(context, resource.message, Toast.LENGTH_SHORT).show()

                        }
                    }
                })

            }
        }

    }

    private fun setupStateFavorite(data: TvShowDetail) {
        binding.btnFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (!isChecked) {
                button.setOnClickListener {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(getString(R.string.confirm_delete))
                        .setMessage(getString(R.string.desc_delete_confirm))
                        .setPositiveButton(
                            getString(R.string.yes_button)
                        ) { _, _ ->
                            viewModel.deleteFavoriteTvShow(data)
                            Toast.makeText(
                                requireContext(),
                                data.name + " " + getString(R.string.deleted),
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
                    viewModel.addFavoriteTvShow(data)
                    Toast.makeText(
                        requireContext(),
                        data.name + " " + getString(R.string.added_to_fav),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                lastFavState = true
            }
        }
    }

    private fun setupToolbar() {
        val title: TextView = requireActivity().findViewById(R.id.toolbar_title)
        val back: ImageButton = requireActivity().findViewById(R.id.toolbar_back)
        back.visibility = View.VISIBLE
        title.text = getString(R.string.detail)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}