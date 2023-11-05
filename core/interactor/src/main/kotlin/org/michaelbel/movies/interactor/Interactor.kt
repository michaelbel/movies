package org.michaelbel.movies.interactor

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