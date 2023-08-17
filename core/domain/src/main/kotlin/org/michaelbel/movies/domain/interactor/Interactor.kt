package org.michaelbel.movies.domain.interactor

import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.interactor.authentication.AuthenticationInteractor
import org.michaelbel.movies.domain.interactor.movie.MovieInteractor
import org.michaelbel.movies.domain.interactor.settings.SettingsInteractor
import javax.inject.Inject

class Interactor @Inject constructor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    movieInteractor: MovieInteractor,
    settingsInteractor: SettingsInteractor
): AccountInteractor by accountInteractor,
   AuthenticationInteractor by authenticationInteractor,
   MovieInteractor by movieInteractor,
   SettingsInteractor by settingsInteractor