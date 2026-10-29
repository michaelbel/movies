@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package org.michaelbel.movies.webui.theme

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.ui.theme.AppTheme

@Composable
fun WebTheme(
    themeData: ThemeData = ThemeData.Default,
    content: @Composable () -> Unit
) {
    AppTheme(
        themeData = themeData
    ) {
        MaterialExpressiveTheme(
            colorScheme = MaterialTheme.colorScheme,
            motionScheme = MaterialTheme.motionScheme,
            shapes = MaterialTheme.shapes,
            typography = MoviesTypography(),
            content = content
        )
    }
}
