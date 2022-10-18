package org.michaelbel.movies.feed.ktx

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

private val <T: Any> LazyPagingItems<T>.isNotEmpty: Boolean
    get() = itemCount > 0

internal val <T: Any> LazyPagingItems<T>.isEmpty: Boolean
    get() = itemCount == 0

internal val <T: Any> LazyPagingItems<T>.isLoading: Boolean
    get() = loadState.refresh is LoadState.Loading && isEmpty

internal val <T: Any> LazyPagingItems<T>.isFailure: Boolean
    get() = loadState.refresh is LoadState.Error && isEmpty

internal val <T: Any> LazyPagingItems<T>.isPagingLoading: Boolean
    get() = loadState.append is LoadState.Loading && isNotEmpty

internal val <T: Any> LazyPagingItems<T>.isPagingFailure: Boolean
    get() = loadState.append is LoadState.Error && isNotEmpty

internal val <T: Any> LazyPagingItems<T>.throwable: Throwable
    get() = (loadState.refresh as LoadState.Error).error