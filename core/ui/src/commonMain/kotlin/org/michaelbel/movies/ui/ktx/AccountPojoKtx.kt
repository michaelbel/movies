package org.michaelbel.movies.ui.ktx

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.ktx.letters

val AccountPojo.lettersTextFontSizeSmall: TextUnit
    @Composable get() = if (letters.length == 1) 16.sp else 13.sp

val AccountPojo.lettersTextFontSizeLarge: TextUnit
    @Composable get() = if (letters.length == 1) 32.sp else 26.sp