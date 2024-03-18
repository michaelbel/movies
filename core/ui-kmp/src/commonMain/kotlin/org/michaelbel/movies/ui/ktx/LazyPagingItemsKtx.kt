@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import androidx.paging.compose.LazyPagingItems

expect val <T: Any> LazyPagingItems<T>.isNotEmpty: Boolean

expect val <T: Any> LazyPagingItems<T>.isEmpty: Boolean

expect val <T: Any> LazyPagingItems<T>.isLoading: Boolean

expect val <T: Any> LazyPagingItems<T>.isFailure: Boolean

expect val <T: Any> LazyPagingItems<T>.isPagingLoading: Boolean

expect val <T: Any> LazyPagingItems<T>.isPagingFailure: Boolean

expect val <T: Any> LazyPagingItems<T>.isRefreshError: Boolean

expect val <T: Any> LazyPagingItems<T>.isAppendError: Boolean

expect val <T: Any> LazyPagingItems<T>.refreshThrowable: Throwable

expect val <T: Any> LazyPagingItems<T>.appendThrowable: Throwable

expect val <T: Any> LazyPagingItems<T>.isAppendLoading: Boolean

expect val <T: Any> LazyPagingItems<T>.isAppendRefresh: Boolean