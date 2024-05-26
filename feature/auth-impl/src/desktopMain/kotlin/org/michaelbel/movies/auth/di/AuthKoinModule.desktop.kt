package org.michaelbel.movies.auth.di

import org.koin.dsl.module
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val authKoinModule = module {
    includes(
        interactorKoinModule
    )
    single { AuthViewModel(get()) }
}