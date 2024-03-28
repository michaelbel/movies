package org.michaelbel.movies.account.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.interactor.di.interactorKoinModule
import org.michaelbel.movies.account.AccountViewModel

val accountKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::AccountViewModel)
}