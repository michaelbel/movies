package org.michaelbel.moviemade.presentation.features.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import org.michaelbel.moviemade.data.model.MovieResponse
import org.michaelbel.moviemade.databinding.ListitemMovieLinearBinding

class MoviesPagingAdapter(
    private val clickAction: (movie: MovieResponse) -> Unit
): PagingDataAdapter<MovieResponse, MovieViewHolder>(DIFF_UTIL_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ListitemMovieLinearBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            clickAction
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieResponse: MovieResponse? = getItem(position)
        movieResponse?.let { holder.bind(it) }
    }

    private companion object {
        private val DIFF_UTIL_COMPARATOR = object: DiffUtil.ItemCallback<MovieResponse>() {
            override fun areItemsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: MovieResponse,
                newItem: MovieResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}