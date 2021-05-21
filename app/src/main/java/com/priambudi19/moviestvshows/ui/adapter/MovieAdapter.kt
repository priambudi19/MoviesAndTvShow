package com.priambudi19.moviestvshows.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.transform.RoundedCornersTransformation
import com.priambudi19.moviestvshows.data.model.movie.Movie
import com.priambudi19.moviestvshows.databinding.RowItemBinding
import com.priambudi19.moviestvshows.utils.MovieUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
            holder.binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(movie)
            }
        }
    }

    class MovieViewHolder(val binding: RowItemBinding) :
        RecyclerView.ViewHolder(binding.root), KoinComponent {
        private val imageLoader: ImageLoader by inject()
        fun bind(movie: Movie) {
            with(binding) {
                tvTitleList.text = movie.title
                tvReleaseDateList.text = MovieUtil.formatRelease(movie.releaseDate)
                tvOverviewList.text = movie.overview
                progressListScore.progress = movie.voteAverage.roundToInt()
                tvScoreValueRow.text = movie.voteAverage.toString()
                imageView.load(
                    MovieUtil.getImageUrl(200, movie.posterPath),
                    imageLoader = imageLoader
                ) {
                    transformations(RoundedCornersTransformation(16f))
                }

            }

        }


    }

    private var onItemClickCallback: OnItemClickCallback? = null


    interface OnItemClickCallback {
        fun onItemClicked(data: Movie)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }


}
