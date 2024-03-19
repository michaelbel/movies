package org.michaelbel.movies.ui.ktx

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.michaelbel.movies.common.exceptions.PageEmptyException

internal val <T: Any> LazyPagingItems<T>.isNotEmpty: Boolean
    get() = itemCount > 0

internal val <T: Any> LazyPagingItems<T>.isEmpty: Boolean
    get() = itemCount == 0

actual val <T: Any> LazyPagingItems<T>.isLoading: Boolean
    get() = loadState.refresh is LoadState.Loading && isEmpty

actual val <T: Any> LazyPagingItems<T>.isFailure: Boolean
    get() = loadState.refresh is LoadState.Error && isEmpty

internal val <T: Any> LazyPagingItems<T>.isPagingLoading: Boolean
    get() = isNotEmpty && (isAppendLoading || isAppendRefresh)

internal val <T: Any> LazyPagingItems<T>.isPagingFailure: Boolean
    get() = isNotEmpty && (isAppendError && appendThrowable !is PageEmptyException || isRefreshError && refreshThrowable !is PageEmptyException)

internal val <T: Any> LazyPagingItems<T>.isRefreshError: Boolean
    get() = loadState.refresh is LoadState.Error

internal val <T: Any> LazyPagingItems<T>.isAppendError: Boolean
    get() = loadState.append is LoadState.Error

actual val <T: Any> LazyPagingItems<T>.refreshThrowable: Throwable
    get() = (loadState.refresh as LoadState.Error).error

internal val <T: Any> LazyPagingItems<T>.appendThrowable: Throwable
    get() = (loadState.append as LoadState.Error).error

internal val <T: Any> LazyPagingItems<T>.isAppendLoading: Boolean
    get() = loadState.append is LoadState.Loading

internal val <T: Any> LazyPagingItems<T>.isAppendRefresh: Boolean
    get() = loadState.refresh is LoadState.Loading