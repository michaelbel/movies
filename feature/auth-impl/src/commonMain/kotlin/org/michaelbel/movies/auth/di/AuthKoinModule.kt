package org.michaelbel.movies.auth.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.auth.AuthViewModel

val authKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::AuthViewModel)
}