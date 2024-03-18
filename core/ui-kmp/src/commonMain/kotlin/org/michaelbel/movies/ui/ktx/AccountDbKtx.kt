@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import androidx.compose.ui.unit.TextUnit
import org.michaelbel.movies.persistence.database.entity.AccountDb

expect val AccountDb.lettersTextFontSizeSmall: TextUnit

expect val AccountDb.lettersTextFontSizeLarge: TextUnit