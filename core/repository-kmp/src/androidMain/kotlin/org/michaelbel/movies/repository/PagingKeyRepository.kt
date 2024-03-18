@file:Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING"
)

package org.michaelbel.movies.repository

actual interface PagingKeyRepository {

    suspend fun page(pagingKey: String): Int?

    suspend fun totalPages(pagingKey: String): Int?

    suspend fun prevPage(pagingKey: String): Int?

    suspend fun removePagingKey(pagingKey: String)

    suspend fun insertPagingKey(pagingKey: String, page: Int, totalPages: Int)
}