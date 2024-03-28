@file:OptIn(ExperimentalResourceApi::class)

package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import movies.feature.settings_impl_kmp.generated.resources.Res
import movies.feature.settings_impl_kmp.generated.resources.settings_appearance_grid
import movies.feature.settings_impl_kmp.generated.resources.settings_appearance_list
import movies.feature.settings_impl_kmp.generated.resources.settings_gender_feminine
import movies.feature.settings_impl_kmp.generated.resources.settings_gender_masculine
import movies.feature.settings_impl_kmp.generated.resources.settings_gender_neutral
import movies.feature.settings_impl_kmp.generated.resources.settings_gender_not_specified
import movies.feature.settings_impl_kmp.generated.resources.settings_language_en
import movies.feature.settings_impl_kmp.generated.resources.settings_language_ru
import movies.feature.settings_impl_kmp.generated.resources.settings_movie_list_now_playing
import movies.feature.settings_impl_kmp.generated.resources.settings_movie_list_popular
import movies.feature.settings_impl_kmp.generated.resources.settings_movie_list_top_rated
import movies.feature.settings_impl_kmp.generated.resources.settings_movie_list_upcoming
import movies.feature.settings_impl_kmp.generated.resources.settings_theme_amoled
import movies.feature.settings_impl_kmp.generated.resources.settings_theme_dark
import movies.feature.settings_impl_kmp.generated.resources.settings_theme_light
import movies.feature.settings_impl_kmp.generated.resources.settings_theme_system
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.michaelbel.movies.common.SealedString
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme

val SealedString.stringText: String
    @Composable get() = when (this) {
        is AppLanguage.English -> stringResource(Res.string.settings_language_en)
        is AppLanguage.Russian -> stringResource(Res.string.settings_language_ru)

        is AppTheme.NightNo -> stringResource(Res.string.settings_theme_light)
        is AppTheme.NightYes -> stringResource(Res.string.settings_theme_dark)
        is AppTheme.FollowSystem -> stringResource(Res.string.settings_theme_system)
        is AppTheme.Amoled -> stringResource(Res.string.settings_theme_amoled)

        is FeedView.FeedList -> stringResource(Res.string.settings_appearance_list)
        is FeedView.FeedGrid -> stringResource(Res.string.settings_appearance_grid)

        is GrammaticalGender.NotSpecified -> stringResource(Res.string.settings_gender_not_specified)
        is GrammaticalGender.Neutral -> stringResource(Res.string.settings_gender_neutral)
        is GrammaticalGender.Feminine -> stringResource(Res.string.settings_gender_feminine)
        is GrammaticalGender.Masculine -> stringResource(Res.string.settings_gender_masculine)

        is MovieList.NowPlaying -> stringResource(Res.string.settings_movie_list_now_playing)
        is MovieList.Popular -> stringResource(Res.string.settings_movie_list_popular)
        is MovieList.TopRated -> stringResource(Res.string.settings_movie_list_top_rated)
        is MovieList.Upcoming -> stringResource(Res.string.settings_movie_list_upcoming)

        else -> ""
    }