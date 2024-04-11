package org.michaelbel.movies.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import org.michaelbel.movies.ui.R

private val OpenSans = FontFamily(
    Font(R.font.open_sans),
    Font(R.font.open_sans_bold),
    Font(R.font.open_sans_bold_italic),
    Font(R.font.open_sans_condensed_bold),
    Font(R.font.open_sans_condensed_light),
    Font(R.font.open_sans_condensed_light_italic),
    Font(R.font.open_sans_extrabold),
    Font(R.font.open_sans_extrabold_italic),
    Font(R.font.open_sans_italic),
    Font(R.font.open_sans_light),
    Font(R.font.open_sans_light_italic),
    Font(R.font.open_sans_semibold),
    Font(R.font.open_sans_semibold_italic)
)

val MoviesTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.open_sans_semibold))
    )
)