package org.michaelbel.movies.gallery

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.network.model.ImagesResponse

@HiltViewModel
class GalleryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor
): BaseViewModel() {

    private val movieId: String = savedStateHandle.require("movieId")

    val imageFlow: StateFlow<String> = interactor.movieImage(movieId.toInt())
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = ""
        )

    init {
        launch {
            val imagesResponse: ImagesResponse = interactor.movieImages(movieId.toInt())
        }
    }
}