package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.data.ktx.letters

val AccountDb.lettersTextFontSizeSmall: TextUnit
    @Composable get() = if (letters.length == 1) 16.sp else 13.sp

val AccountDb.lettersTextFontSizeLarge: TextUnit
    @Composable get() = if (letters.length == 1) 32.sp else 26.sp