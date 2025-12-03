package org.michaelbel.movies.gallery

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.mvi.MoviesViewModel
import org.michaelbel.movies.gallery.intent.GalleryIntent
import org.michaelbel.movies.gallery.model.GalleryModel
import org.michaelbel.movies.interactor.Interactor
import org.michaelbel.movies.ui.navigation.GalleryDestination
import org.michaelbel.movies.ui.navigation.MainNavigator
import org.michaelbel.movies.work.WorkManagerInteractor

class GalleryViewModel(
    private val destination: GalleryDestination,
    private val interactor: Interactor,
    private val workManagerInteractor: WorkManagerInteractor
): MoviesViewModel<GalleryModel, GalleryIntent>(GalleryModel()) {

    init {
        dispatch(GalleryIntent.CollectMovieImages)
        dispatch(GalleryIntent.LoadMovieImages)
    }

    override fun dispatch(intent: GalleryIntent) {
        when (intent) {
            is GalleryIntent.CollectMovieImages -> {
                launch {
                    interactor.imagesFlow(destination.movieId).collectLatest { movieImages ->
                        reduce { it.copy(movieImages = movieImages) }
                    }
                }
            }
            is GalleryIntent.BackClick -> launch { MainNavigator.back() }
            is GalleryIntent.LoadMovieImages -> launch { interactor.images(destination.movieId) }
            is GalleryIntent.DownloadClick -> {
                launch {
                    workManagerInteractor.downloadImage(intent.image).collectLatest { workInfoState ->
                        reduce { it.copy(workInfoState = workInfoState) }
                    }
                }
            }
        }
    }
}