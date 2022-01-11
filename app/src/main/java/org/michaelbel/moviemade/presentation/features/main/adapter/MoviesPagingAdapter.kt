package org.michaelbel.moviemade.presentation.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.michaelbel.data.remote.model.MovieResponse
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.databinding.ListitemMovieLinearBinding

class MoviesPagingAdapter(
    private val clickAction: (movie: MovieResponse) -> Unit
): PagingDataAdapter<MovieResponse, ViewHolder>(UIMODEL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return MovieViewHolder(
            ListitemMovieLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickAction
        )
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.listitem_movie_linear
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            (holder as MovieViewHolder).bind(uiModel)
        }
    }

    companion object {
        private val UIMODEL_COMPARATOR = object: DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean =
                    oldItem == newItem
        }
    }
}