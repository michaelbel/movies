package org.michaelbel.movies.gallery.di

import org.koin.dsl.module
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val galleryKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { GalleryViewModel(get()) }
}