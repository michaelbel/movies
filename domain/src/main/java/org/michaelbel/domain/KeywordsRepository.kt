package org.michaelbel.domain

import org.michaelbel.data.remote.Api
import org.michaelbel.data.remote.model.KeywordsResponse
import retrofit2.Response
import javax.inject.Inject

class KeywordsRepository @Inject constructor(
    private val api: Api
) {

    suspend fun keywords(movieId: Long, apiKey: String): Response<KeywordsResponse> {
        return api.keywords(movieId, apiKey)
    }
}