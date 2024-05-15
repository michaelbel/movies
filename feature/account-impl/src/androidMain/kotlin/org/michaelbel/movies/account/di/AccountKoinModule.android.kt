package org.michaelbel.movies.account.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import org.michaelbel.movies.account.AccountViewModel
import org.michaelbel.movies.interactor.di.interactorKoinModule

actual val accountKoinModule = module {
    includes(
        interactorKoinModule
    )
    viewModelOf(::AccountViewModel)
}