package org.michaelbel.movies.gallery.di

import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule

val galleryKoinModule = module {
    includes(
        interactorKoinModule
    )
}