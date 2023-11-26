package org.michaelbel.movies.interactor

import javax.inject.Inject

class Interactor @Inject constructor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    imageInteractor: ImageInteractor,
    movieInteractor: MovieInteractor,
    notificationInteractor: NotificationInteractor,
    pagingKeyInteractor: PagingKeyInteractor,
    settingsInteractor: SettingsInteractor
): AccountInteractor by accountInteractor,
   AuthenticationInteractor by authenticationInteractor,
   ImageInteractor by imageInteractor,
   MovieInteractor by movieInteractor,
   NotificationInteractor by notificationInteractor,
   PagingKeyInteractor by pagingKeyInteractor,
   SettingsInteractor by settingsInteractor