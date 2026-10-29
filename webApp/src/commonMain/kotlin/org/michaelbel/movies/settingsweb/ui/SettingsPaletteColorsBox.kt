@file:OptIn(ExperimentalFoundationApi::class)

package org.michaelbel.movies.settingsweb.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.ThemeData
import org.michaelbel.movies.ui.color.PaletteStyle
import org.michaelbel.movies.ui.color.TonalPalettes.Companion.toTonalPalettes
import org.michaelbel.movies.ui.pagerindicator.HorizontalPagerIndicator
import org.michaelbel.movies.ui.theme.colorList
import org.michaelbel.movies.ui.theme.paletteStyles

@Composable
fun SettingsPaletteColorsBox(
    paletteKey: Int,
    seedColor: Int,
    onChange: (Int, Int) -> Unit
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val paletteItems = remember {
            buildList {
                colorList.forEach { color ->
                    val colorArgb = color.toArgb()
                    paletteStyles.subList(ThemeData.STYLE_TONAL_SPOT, ThemeData.STYLE_MONOCHROME).forEachIndexed { index, style ->
                        add(Triple(index, colorArgb, style))
                    }
                }
                add(Triple(ThemeData.STYLE_MONOCHROME, Color.Black.toArgb(), PaletteStyle.Monochrome))
            }
        }
        val horizontalPaddingPx = with(density) { 16.dp.roundToPx() }
        val horizontalSpacingPx = with(density) { 8.dp.roundToPx() }
        val itemWidthPx = with(density) { 80.dp.roundToPx() }
        val availableWidthPx = (constraints.maxWidth - horizontalPaddingPx * 2).coerceAtLeast(0)
        val itemsPerPage = ((availableWidthPx + horizontalSpacingPx) / (itemWidthPx + horizontalSpacingPx)).coerceAtLeast(1)
        val palettePages = remember(itemsPerPage) { paletteItems.chunked(itemsPerPage) }
        val selectedItemIndex = paletteItems.indexOfFirst { (localPaletteKey, localSeedColor, _) ->
            localPaletteKey == paletteKey && localSeedColor == seedColor
        }.run { if (this == -1) 0 else this }
        val selectedPage = selectedItemIndex / itemsPerPage
        val pagerState = rememberPagerState(initialPage = selectedPage) { palettePages.size }

        LaunchedEffect(selectedPage, palettePages.size) {
            val targetPage = selectedPage.coerceIn(0, palettePages.size - 1)
            if (pagerState.currentPage != targetPage) {
                pagerState.scrollToPage(targetPage)
            }
        }
        
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                modifier = Modifier.fillMaxWidth(),
                state = pagerState
            ) { page ->
                val paletteItemsOnPage = palettePages[page]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(space =  8.dp, alignment = Alignment.CenterHorizontally)
                ) {
                    paletteItemsOnPage.forEach { (localPaletteKey, localSeedColor, localStyle) ->
                        SettingPaletteColor(
                            tonalPalettes = Color(localSeedColor).toTonalPalettes(localStyle),
                            isSelected = paletteKey == localPaletteKey && seedColor == localSeedColor,
                            onClick = { onChange(localPaletteKey, localSeedColor) }
                        )
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(vertical = 12.dp),
                activeColor = MaterialTheme.colorScheme.primary,
                inactiveColor = MaterialTheme.colorScheme.outline,
                indicatorWidth = 6.dp,
                indicatorHeight = 6.dp,
                spacing = 2.dp
            )
        }
    }
}
