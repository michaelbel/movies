package org.michaelbel.moviemade.presentation.features.main.adapter

import coil.load
import org.michaelbel.data.remote.model.MovieResponse
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.TmdbConfig
import org.michaelbel.moviemade.databinding.ListitemMovieLinearBinding

class MovieViewHolder(
    private val binding: ListitemMovieLinearBinding,
    clickAction: (movie: MovieResponse) -> Unit
): BaseViewHolder(binding.root) {

    private lateinit var movie: MovieResponse

    init {
        binding.root.setOnClickListener { clickAction(movie) }
    }

    fun bind(movie: MovieResponse) {
        this.movie = movie

        binding.backdropImageView
            .load(TmdbConfig.image(movie.backdropPathSafe)) {
                crossfade(true)
                placeholder(R.drawable.shape_movie_poster_placeholder)
            }
        binding.titleTextView.text = movie.title
    }
}