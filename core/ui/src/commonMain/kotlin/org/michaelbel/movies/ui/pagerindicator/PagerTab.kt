package org.michaelbel.movies.ui.pagerindicator

internal interface PagerStateBridge {
    val currentPage: Int
    val currentPageOffset: Float
}