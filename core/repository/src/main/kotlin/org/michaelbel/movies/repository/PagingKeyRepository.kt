package org.michaelbel.movies.repository

interface PagingKeyRepository {

    suspend fun page(pagingKey: String): Int?

    suspend fun removePagingKey(pagingKey: String)

    suspend fun insertPagingKey(pagingKey: String, page: Int)
}