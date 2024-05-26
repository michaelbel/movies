package org.michaelbel.movies.auth.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.auth.AuthViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val authKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::AuthViewModel)
}