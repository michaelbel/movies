@file:OptIn(ExperimentalFoundationApi::class)

package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.settings.ui.common.SettingPaletteColor
import org.michaelbel.movies.ui.color.PaletteStyle
import org.michaelbel.movies.ui.color.TonalPalettes.Companion.toTonalPalettes
import org.michaelbel.movies.ui.pagerindicator.HorizontalPagerIndicator
import org.michaelbel.movies.ui.theme.colorList
import org.michaelbel.movies.ui.theme.paletteStyles

@Composable
internal fun SettingsPaletteColorsBox(
    isDynamicColorsEnabled: Boolean,
    paletteKey: Int,
    seedColor: Int,
    onChange: (Boolean, Int, Int) -> Unit
) {
    val pageCount = colorList.size + 1
    val pagerState = rememberPagerState(
        initialPage = if (paletteKey == ThemeData.STYLE_MONOCHROME) pageCount else colorList.indexOf(Color(seedColor)).run { if (this == -1) 0 else this }
    ) { pageCount }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) { page ->
            if (page < pageCount - 1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    paletteStyles.subList(ThemeData.STYLE_TONAL_SPOT, ThemeData.STYLE_MONOCHROME).forEachIndexed { index, style ->
                        val color = colorList[page]
                        val tonalPalettes by remember { mutableStateOf(color.toTonalPalettes(style)) }
                        SettingPaletteColor(
                            tonalPalettes = tonalPalettes,
                            isSelected = !isDynamicColorsEnabled && paletteKey == index && seedColor == color.toArgb(),
                            onClick = { onChange(false, index, color.toArgb()) }
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    SettingPaletteColor(
                        tonalPalettes = Color.Black.toTonalPalettes(PaletteStyle.Monochrome),
                        isSelected = !isDynamicColorsEnabled && paletteKey == ThemeData.STYLE_MONOCHROME,
                        onClick = { onChange(false, ThemeData.STYLE_MONOCHROME, Color.Black.toArgb()) }
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(vertical = 12.dp),
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceContainer else MaterialTheme.colorScheme.outlineVariant,
            indicatorWidth = 6.dp,
            indicatorHeight = 6.dp,
            spacing = 2.dp
        )
    }
}