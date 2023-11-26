package org.michaelbel.movies.interactor

interface PagingKeyInteractor {

    suspend fun page(pagingKey: String): Int?

    suspend fun removePagingKey(pagingKey: String)

    suspend fun insertPagingKey(pagingKey: String, page: Int)
}