package org.michaelbel.movies.settings.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.settings_impl.R

internal val GrammaticalGender.genderText: String
    @Composable get() = when (this) {
        is GrammaticalGender.NotSpecified -> stringResource(R.string.settings_gender_not_specified)
        is GrammaticalGender.Neutral -> stringResource(R.string.settings_gender_neutral)
        is GrammaticalGender.Feminine -> stringResource(R.string.settings_gender_feminine)
        is GrammaticalGender.Masculine -> stringResource(R.string.settings_gender_masculine)
    }