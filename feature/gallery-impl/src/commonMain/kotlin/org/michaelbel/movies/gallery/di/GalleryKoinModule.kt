package org.michaelbel.movies.gallery.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.work.di.workManagerInteractorKoinModule
import org.michaelbel.movies.ui.navigation.GalleryDestination

val galleryKoinModule = module {
    includes(
        interactorKoinModule,
        workManagerInteractorKoinModule
    )
    viewModel { (destination: GalleryDestination) ->
        GalleryViewModel(destination, get(), get())
    }
}