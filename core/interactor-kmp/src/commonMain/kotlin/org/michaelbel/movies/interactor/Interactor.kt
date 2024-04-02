package org.michaelbel.movies.interactor

class Interactor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    imageInteractor: ImageInteractor,
    movieInteractor: MovieInteractor,
    notificationInteractor: NotificationInteractor,
    searchInteractor: SearchInteractor,
    settingsInteractor: SettingsInteractor,
    suggestionInteractor: SuggestionInteractor,
    localeInteractor: LocaleInteractor
): AccountInteractor by accountInteractor,
    AuthenticationInteractor by authenticationInteractor,
    ImageInteractor by imageInteractor,
    MovieInteractor by movieInteractor,
    NotificationInteractor by notificationInteractor,
    SearchInteractor by searchInteractor,
    SettingsInteractor by settingsInteractor,
    SuggestionInteractor by suggestionInteractor,
    LocaleInteractor by localeInteractor