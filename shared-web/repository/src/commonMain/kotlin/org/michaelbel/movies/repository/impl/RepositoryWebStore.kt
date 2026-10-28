package org.michaelbel.movies.repository.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import org.michaelbel.movies.persistence.database.entity.pojo.AccountPojo
import org.michaelbel.movies.persistence.database.entity.pojo.ImagePojo
import org.michaelbel.movies.persistence.database.entity.pojo.SuggestionPojo
import org.michaelbel.movies.persistence.database.typealiases.MovieId

class RepositoryWebStore {

    private val accountState = MutableStateFlow(AccountPojo.Empty)
    private val imagesState = MutableStateFlow<Map<MovieId, List<ImagePojo>>>(emptyMap())
    private val suggestionsState = MutableStateFlow<List<SuggestionPojo>>(emptyList())

    fun accountFlow(): Flow<AccountPojo> {
        return accountState
    }

    fun updateAccount(account: AccountPojo) {
        accountState.value = account
    }

    fun clearAccount() {
        accountState.value = AccountPojo.Empty
    }

    fun imagesFlow(movieId: MovieId): Flow<List<ImagePojo>> {
        return imagesState.map { images ->
            images[movieId].orEmpty().sortedBy(ImagePojo::position)
        }
    }

    fun updateImages(movieId: MovieId, images: List<ImagePojo>) {
        imagesState.value += (movieId to images.sortedBy(ImagePojo::position))
    }

    fun suggestionsFlow(): Flow<List<SuggestionPojo>> {
        return suggestionsState
    }

    fun updateSuggestions(suggestions: List<SuggestionPojo>) {
        suggestionsState.value = suggestions
    }

    fun clearSuggestions() {
        suggestionsState.value = emptyList()
    }
}
