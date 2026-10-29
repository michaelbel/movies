@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.webui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import movies.webapp.generated.resources.Res
import movies.webapp.generated.resources.roboto_bold
import movies.webapp.generated.resources.roboto_medium
import movies.webapp.generated.resources.roboto_regular
import org.jetbrains.compose.resources.Font

@Composable
fun MoviesTypography(): Typography {
    val fontFamily = FontFamily(
        Font(
            resource = Res.font.roboto_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.roboto_medium,
            weight = FontWeight.Medium
        ),
        Font(
            resource = Res.font.roboto_bold,
            weight = FontWeight.Bold
        )
    )

    val typography = Typography()

    return Typography(
        displayLarge = typography.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = typography.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = typography.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = typography.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = typography.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = typography.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = typography.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = typography.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = typography.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = typography.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = typography.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = typography.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = typography.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = typography.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = typography.labelSmall.copy(fontFamily = fontFamily),
        displayLargeEmphasized = typography.displayLargeEmphasized.copy(fontFamily = fontFamily),
        displayMediumEmphasized = typography.displayMediumEmphasized.copy(fontFamily = fontFamily),
        displaySmallEmphasized = typography.displaySmallEmphasized.copy(fontFamily = fontFamily),
        headlineLargeEmphasized = typography.headlineLargeEmphasized.copy(fontFamily = fontFamily),
        headlineMediumEmphasized = typography.headlineMediumEmphasized.copy(fontFamily = fontFamily),
        headlineSmallEmphasized = typography.headlineSmallEmphasized.copy(fontFamily = fontFamily),
        titleLargeEmphasized = typography.titleLargeEmphasized.copy(fontFamily = fontFamily),
        titleMediumEmphasized = typography.titleMediumEmphasized.copy(fontFamily = fontFamily),
        titleSmallEmphasized = typography.titleSmallEmphasized.copy(fontFamily = fontFamily),
        bodyLargeEmphasized = typography.bodyLargeEmphasized.copy(fontFamily = fontFamily),
        bodyMediumEmphasized = typography.bodyMediumEmphasized.copy(fontFamily = fontFamily),
        bodySmallEmphasized = typography.bodySmallEmphasized.copy(fontFamily = fontFamily),
        labelLargeEmphasized = typography.labelLargeEmphasized.copy(fontFamily = fontFamily),
        labelMediumEmphasized = typography.labelMediumEmphasized.copy(fontFamily = fontFamily),
        labelSmallEmphasized = typography.labelSmallEmphasized.copy(fontFamily = fontFamily)
    )
}
