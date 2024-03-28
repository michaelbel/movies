@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.ui.strings.MoviesStrings

val SealedString.stringText: String
    @Composable get() = when (this) {
        is AppLanguage.English -> stringResource(MoviesStrings.settings_language_en)
        is AppLanguage.Russian -> stringResource(MoviesStrings.settings_language_ru)

        is AppTheme.NightNo -> stringResource(MoviesStrings.settings_theme_light)
        is AppTheme.NightYes -> stringResource(MoviesStrings.settings_theme_dark)
        is AppTheme.FollowSystem -> stringResource(MoviesStrings.settings_theme_system)
        is AppTheme.Amoled -> stringResource(MoviesStrings.settings_theme_amoled)

        is FeedView.FeedList -> stringResource(MoviesStrings.settings_appearance_list)
        is FeedView.FeedGrid -> stringResource(MoviesStrings.settings_appearance_grid)

        is GrammaticalGender.NotSpecified -> stringResource(MoviesStrings.settings_gender_not_specified)
        is GrammaticalGender.Neutral -> stringResource(MoviesStrings.settings_gender_neutral)
        is GrammaticalGender.Feminine -> stringResource(MoviesStrings.settings_gender_feminine)
        is GrammaticalGender.Masculine -> stringResource(MoviesStrings.settings_gender_masculine)

        is MovieList.NowPlaying -> stringResource(MoviesStrings.settings_movie_list_now_playing)
        is MovieList.Popular -> stringResource(MoviesStrings.settings_movie_list_popular)
        is MovieList.TopRated -> stringResource(MoviesStrings.settings_movie_list_top_rated)
        is MovieList.Upcoming -> stringResource(MoviesStrings.settings_movie_list_upcoming)

        else -> ""
    }