@file:Suppress(
    "ACTUAL_CLASSIFIER_MUST_HAVE_THE_SAME_MEMBERS_AS_NON_FINAL_EXPECT_CLASSIFIER_WARNING",
    "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING",
    "NON_ACTUAL_MEMBER_DECLARED_IN_EXPECT_NON_FINAL_CLASSIFIER_ACTUALIZATION_WARNING"
)

package org.michaelbel.movies.interactor

import org.michaelbel.movies.interactor.entity.Password
import org.michaelbel.movies.interactor.entity.Username
import org.michaelbel.movies.network.model.Session
import org.michaelbel.movies.network.model.Token

actual interface AuthenticationInteractor {

    suspend fun createRequestToken(loginViaTmdb: Boolean): Token

    suspend fun createSessionWithLogin(
        username: Username,
        password: Password,
        requestToken: String
    ): Token

    suspend fun createSession(token: String): Session

    suspend fun deleteSession()
}