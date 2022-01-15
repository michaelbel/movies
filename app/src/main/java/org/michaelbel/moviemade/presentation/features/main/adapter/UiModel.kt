package org.michaelbel.moviemade.presentation.features.main.adapter

import org.michaelbel.data.remote.model.MovieResponse

sealed class UiModel {
    data class MovieItem(val movie: MovieResponse) : UiModel()
    data class AdItem(val description: String) : UiModel()
}