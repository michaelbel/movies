package org.michaelbel.movies.gallery.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.work.di.workKoinModule

actual val galleryKoinModule = module {
    includes(
        interactorKoinModule,
        workKoinModule
    )
    viewModel { GalleryViewModel(get(), get(), get()) }
}