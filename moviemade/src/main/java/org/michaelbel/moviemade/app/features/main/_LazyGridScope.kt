package org.michaelbel.moviemade.app.features.main

import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

fun <T: Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    content: @Composable LazyItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        content(lazyPagingItems[index])
    }
}