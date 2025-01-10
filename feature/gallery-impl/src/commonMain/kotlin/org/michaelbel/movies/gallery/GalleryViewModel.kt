package org.michaelbel.movies.gallery

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.ktx.require
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId
import org.michaelbel.movies.work.WorkInfoState
import org.michaelbel.movies.work.WorkManagerInteractor

class GalleryViewModel(
    savedStateHandle: SavedStateHandle,
    private val interactor: Interactor,
    private val workManagerInteractor: WorkManagerInteractor
): BaseViewModel() {

    private val movieId: Int = savedStateHandle.require("movieId")

    val movieImagesFlow: StateFlow<List<ImagePojo>> = interactor.imagesFlow(movieId)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    private val _workInfoStateFlow: MutableStateFlow<WorkInfoState> = MutableStateFlow(WorkInfoState.None)
    val workInfoStateFlow: StateFlow<WorkInfoState> get() = _workInfoStateFlow.asStateFlow()

    init {
        loadMovieImages(movieId)
    }

    fun downloadImage(image: ImagePojo) = scope.launch {
        workManagerInteractor.downloadImage(image).collect { workInfoState ->
            _workInfoStateFlow.emit(workInfoState)
        }
    }

    private fun loadMovieImages(movieId: MovieId) = scope.launch {
        interactor.images(movieId)
    }
}