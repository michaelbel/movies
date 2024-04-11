@file:OptIn(ExperimentalFoundationApi::class)

package org.michaelbel.movies.ui.pagerindicator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.sign

@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageCount: Int = pagerState.pageCount,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape
) {
    val stateBridge = remember(pagerState) {
        object: PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    HorizontalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}

@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape
) {
    val stateBridge = remember(pagerState) {
        object: PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    HorizontalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}

@Composable
private fun HorizontalPagerIndicator(
    pagerState: PagerStateBridge,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorWidth: Dp = 8.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = indicatorWidth,
    indicatorShape: Shape = CircleShape,
) {
    val indicatorWidthPx = LocalDensity.current.run { indicatorWidth.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)

            repeat(pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            modifier = Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffset
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(0F, (pageCount - 1).coerceAtLeast(0).toFloat())

                    IntOffset(
                        x = ((spacingPx + indicatorWidthPx) * scrollPosition).toInt(),
                        y = 0
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .then(
                    if (pageCount > 0) Modifier.background(
                        color = activeColor,
                        shape = indicatorShape,
                    )
                    else Modifier
                )
        )
    }
}

@Composable
fun VerticalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    pageCount: Int = pagerState.pageCount,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorHeight: Dp = 8.dp,
    indicatorWidth: Dp = indicatorHeight,
    spacing: Dp = indicatorHeight,
    indicatorShape: Shape = CircleShape,
) {
    val stateBridge = remember(pagerState) {
        object: PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    VerticalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}

@Composable
fun VerticalPagerIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorHeight: Dp = 8.dp,
    indicatorWidth: Dp = indicatorHeight,
    spacing: Dp = indicatorHeight,
    indicatorShape: Shape = CircleShape,
) {
    val stateBridge = remember(pagerState) {
        object: PagerStateBridge {
            override val currentPage: Int
                get() = pagerState.currentPage
            override val currentPageOffset: Float
                get() = pagerState.currentPageOffsetFraction
        }
    }

    VerticalPagerIndicator(
        pagerState = stateBridge,
        pageCount = pageCount,
        modifier = modifier,
        pageIndexMapping = pageIndexMapping,
        activeColor = activeColor,
        inactiveColor = inactiveColor,
        indicatorHeight = indicatorHeight,
        indicatorWidth = indicatorWidth,
        spacing = spacing,
        indicatorShape = indicatorShape
    )
}

@Composable
private fun VerticalPagerIndicator(
    pagerState: PagerStateBridge,
    pageCount: Int,
    modifier: Modifier = Modifier,
    pageIndexMapping: (Int) -> Int = { it },
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = activeColor.copy(ContentAlpha.disabled),
    indicatorHeight: Dp = 8.dp,
    indicatorWidth: Dp = indicatorHeight,
    spacing: Dp = indicatorHeight,
    indicatorShape: Shape = CircleShape,
) {

    val indicatorHeightPx = LocalDensity.current.run { indicatorHeight.roundToPx() }
    val spacingPx = LocalDensity.current.run { spacing.roundToPx() }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = inactiveColor, shape = indicatorShape)

            repeat(pageCount) {
                Box(indicatorModifier)
            }
        }

        Box(
            modifier = Modifier
                .offset {
                    val position = pageIndexMapping(pagerState.currentPage)
                    val offset = pagerState.currentPageOffset
                    val next = pageIndexMapping(pagerState.currentPage + offset.sign.toInt())
                    val scrollPosition = ((next - position) * offset.absoluteValue + position)
                        .coerceIn(0F, (pageCount - 1).coerceAtLeast(0).toFloat())

                    IntOffset(
                        x = 0,
                        y = ((spacingPx + indicatorHeightPx) * scrollPosition).toInt(),
                    )
                }
                .size(width = indicatorWidth, height = indicatorHeight)
                .then(
                    if (pageCount > 0) Modifier.background(
                        color = activeColor,
                        shape = indicatorShape,
                    )
                    else Modifier
                )
        )
    }
}