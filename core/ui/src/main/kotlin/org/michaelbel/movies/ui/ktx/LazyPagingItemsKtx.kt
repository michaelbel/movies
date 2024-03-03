package org.michaelbel.movies.ui.ktx

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import org.michaelbel.movies.common.exceptions.PageEmptyException

val <T: Any> LazyPagingItems<T>.isNotEmpty: Boolean
    get() = itemCount > 0

val <T: Any> LazyPagingItems<T>.isEmpty: Boolean
    get() = itemCount == 0

val <T: Any> LazyPagingItems<T>.isLoading: Boolean
    get() = loadState.refresh is LoadState.Loading && isEmpty

val <T: Any> LazyPagingItems<T>.isFailure: Boolean
    get() = loadState.refresh is LoadState.Error && isEmpty

val <T: Any> LazyPagingItems<T>.isPagingLoading: Boolean
    get() = loadState.append is LoadState.Loading && isNotEmpty

val <T: Any> LazyPagingItems<T>.isPagingFailure: Boolean
    get() = isNotEmpty && (isAppendError && appendThrowable !is PageEmptyException || isRefreshError && refreshThrowable !is PageEmptyException)

val <T: Any> LazyPagingItems<T>.isRefreshError: Boolean
    get() = loadState.refresh is LoadState.Error

val <T: Any> LazyPagingItems<T>.isAppendError: Boolean
    get() = loadState.append is LoadState.Error

val <T: Any> LazyPagingItems<T>.refreshThrowable: Throwable
    get() = (loadState.refresh as LoadState.Error).error

val <T: Any> LazyPagingItems<T>.appendThrowable: Throwable
    get() = (loadState.append as LoadState.Error).error