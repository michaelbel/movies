package org.michaelbel.movies.interactor

class Interactor(
    accountInteractor: AccountInteractor,
    authenticationInteractor: AuthenticationInteractor,
    imageInteractor: ImageInteractor,
    notificationInteractor: NotificationInteractor,
    searchInteractor: SearchInteractor,
    settingsInteractor: SettingsInteractor,
    suggestionInteractor: SuggestionInteractor,
    localeInteractor: LocaleInteractor
): AccountInteractor by accountInteractor,
    AuthenticationInteractor by authenticationInteractor,
    ImageInteractor by imageInteractor,
    NotificationInteractor by notificationInteractor,
    SearchInteractor by searchInteractor,
    SettingsInteractor by settingsInteractor,
    SuggestionInteractor by suggestionInteractor,
    LocaleInteractor by localeInteractor
