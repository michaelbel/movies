package org.michaelbel.movies.gallery

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.model.ImagesResponse
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieId: String = savedStateHandle.require("movieId")

    init {
        launch {
            val imagesResponse: ImagesResponse = interactor.movieImages(movieId.toInt())
        }
    }
}