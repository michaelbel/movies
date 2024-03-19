@file:Suppress("EXPECT_AND_ACTUAL_IN_THE_SAME_MODULE")

package org.michaelbel.movies.ui.ktx

import androidx.paging.compose.LazyPagingItems

expect val <T: Any> LazyPagingItems<T>.isLoading: Boolean

expect val <T: Any> LazyPagingItems<T>.isFailure: Boolean

expect val <T: Any> LazyPagingItems<T>.refreshThrowable: Throwable