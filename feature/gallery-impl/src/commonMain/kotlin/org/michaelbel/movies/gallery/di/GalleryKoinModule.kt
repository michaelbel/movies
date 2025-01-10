package org.michaelbel.movies.gallery.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.gallery.GalleryViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.work.di.workManagerInteractorKoinModule

val galleryKoinModule = module {
    includes(
        interactorKoinModule,
        workManagerInteractorKoinModule
    )
    viewModelOf(::GalleryViewModel)
}