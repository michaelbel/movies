package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings_impl.R

internal val SealedString.stringText: String
    @Composable get() = when (this) {
        is AppLanguage.English -> stringResource(R.string.settings_language_en)
        is AppLanguage.Russian -> stringResource(R.string.settings_language_ru)

        is AppTheme.NightNo -> stringResource(R.string.settings_theme_light)
        is AppTheme.NightYes -> stringResource(R.string.settings_theme_dark)
        is AppTheme.FollowSystem -> stringResource(R.string.settings_theme_system)
        is AppTheme.Amoled -> stringResource(R.string.settings_theme_amoled)

        is FeedView.FeedList -> stringResource(R.string.settings_appearance_list)
        is FeedView.FeedGrid -> stringResource(R.string.settings_appearance_grid)

        is GrammaticalGender.NotSpecified -> stringResource(R.string.settings_gender_not_specified)
        is GrammaticalGender.Neutral -> stringResource(R.string.settings_gender_neutral)
        is GrammaticalGender.Feminine -> stringResource(R.string.settings_gender_feminine)
        is GrammaticalGender.Masculine -> stringResource(R.string.settings_gender_masculine)

        is MovieList.NowPlaying -> stringResource(R.string.settings_movie_list_now_playing)
        is MovieList.Popular -> stringResource(R.string.settings_movie_list_popular)
        is MovieList.TopRated -> stringResource(R.string.settings_movie_list_top_rated)
        is MovieList.Upcoming -> stringResource(R.string.settings_movie_list_upcoming)

        else -> ""
    }