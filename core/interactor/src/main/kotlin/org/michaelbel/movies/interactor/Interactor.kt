package org.michaelbel.movies.interactor

import javax.inject.Inject

class Interactor @Inject constructor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    imageInteractor: ImageInteractor,
    movieInteractor: MovieInteractor,
    notificationInteractor: NotificationInteractor,
    pagingKeyInteractor: PagingKeyInteractor,
    searchInteractor: SearchInteractor,
    settingsInteractor: SettingsInteractor,
    suggestionInteractor: SuggestionInteractor
): AccountInteractor by accountInteractor,
   AuthenticationInteractor by authenticationInteractor,
   ImageInteractor by imageInteractor,
   MovieInteractor by movieInteractor,
   NotificationInteractor by notificationInteractor,
   PagingKeyInteractor by pagingKeyInteractor,
   SearchInteractor by searchInteractor,
   SettingsInteractor by settingsInteractor,
   SuggestionInteractor by suggestionInteractor