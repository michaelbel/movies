package org.michaelbel.movies.interactor

import javax.inject.Inject

class Interactor @Inject constructor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    imageInteractor: ImageInteractor,
    movieInteractor: MovieInteractor,
    settingsInteractor: SettingsInteractor
): AccountInteractor by accountInteractor,
   AuthenticationInteractor by authenticationInteractor,
   ImageInteractor by imageInteractor,
   MovieInteractor by movieInteractor,
   SettingsInteractor by settingsInteractor