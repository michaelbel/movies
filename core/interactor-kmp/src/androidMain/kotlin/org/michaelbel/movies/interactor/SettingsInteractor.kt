@file:Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.interactor

import kotlinx.coroutines.flow.Flow
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.common.version.AppVersionData

actual interface SettingsInteractor {

    val isReviewFeatureEnabled: Boolean

    val isUpdateFeatureEnabled: Boolean

    val currentTheme: Flow<AppTheme>

    val currentFeedView: Flow<FeedView>

    val currentMovieList: Flow<MovieList>

    val dynamicColors: Flow<Boolean>

    val isBiometricEnabled: Flow<Boolean>

    val isSettingsIconVisible: Flow<Boolean>

    val isPlayServicesAvailable: Flow<Boolean>

    val appVersionData: Flow<AppVersionData>

    suspend fun isBiometricEnabledAsync(): Boolean

    suspend fun selectTheme(appTheme: AppTheme)

    suspend fun selectFeedView(feedView: FeedView)

    suspend fun selectMovieList(movieList: MovieList)

    suspend fun setDynamicColors(value: Boolean)

    suspend fun setBiometricEnabled(enabled: Boolean)

    suspend fun fetchRemoteConfig()
}