package com.priambudi19.moviestvshows.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.priambudi19.moviestvshows.data.model.tvshow.TvShowDetail
import com.priambudi19.moviestvshows.databinding.RowItemBinding
import com.priambudi19.moviestvshows.utils.MovieUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.roundToInt

class TvShowFavoriteAdapter :
    PagingDataAdapter<TvShowDetail, TvShowFavoriteAdapter.TvShowViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
            holder.binding.root.setOnClickListener { onItemClickCallback?.onItemClicked(tvShow) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder =
        TvShowViewHolder(
            RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    class TvShowViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        private val imageLoader : ImageLoader by inject()
        fun bind(tvShow: TvShowDetail) {
            with(binding) {
                tvTitleList.text = tvShow.name
                tvReleaseDateList.text = MovieUtil.formatRelease(tvShow.firstAirDate)
                tvOverviewList.text = tvShow.overview
                progressListScore.progress = tvShow.voteAverage.roundToInt()
                tvScoreValueRow.text = tvShow.voteAverage.toString()
                imageView.load(MovieUtil.getImageUrl(200, tvShow.posterPath),imageLoader)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowDetail>() {
            override fun areItemsTheSame(oldItem: TvShowDetail, newItem: TvShowDetail): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowDetail, newItem: TvShowDetail): Boolean {
                return oldItem == newItem
            }
        }
    }


    private var onItemClickCallback: OnItemClickCallback? = null


    interface OnItemClickCallback {
        fun onItemClicked(data: TvShowDetail)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}