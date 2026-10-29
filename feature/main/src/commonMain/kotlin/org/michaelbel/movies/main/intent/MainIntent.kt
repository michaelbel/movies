package org.michaelbel.movies.main.intent

import org.michaelbel.movies.common.mvi.Intent
import org.michaelbel.movies.persistence.database.typealiases.MovieId

sealed interface MainIntent: Intent {
    data object OpenFeed: MainIntent
    data object OpenSettings: MainIntent
    data object CollectThemeData: MainIntent
    data object CollectScreenshotBlockEnabled: MainIntent
    data object FetchBiometric: MainIntent
    data object BiometricSuccess: MainIntent
    data object BiometricCancel: MainIntent
    data object FetchRemoteConfig: MainIntent
    data object FetchFirebaseMessagingToken: MainIntent
    data object PrepopulateDatabase: MainIntent
    data object UpdateAccountDetails: MainIntent
    data object ShortcutSearchClick: MainIntent
    data object ShortcutSettingsClick: MainIntent
    data class RequestReview(val activity: Any): MainIntent
    data class RequestUpdate(val activity: Any): MainIntent
    data class NavigateToDetails(val movieId: MovieId): MainIntent
    data class NavigateToMain(val requestToken: String, val approved: Boolean): MainIntent
}
