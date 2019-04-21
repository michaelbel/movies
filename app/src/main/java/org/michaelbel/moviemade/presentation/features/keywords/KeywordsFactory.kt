package org.michaelbel.moviemade.presentation.features.keywords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.michaelbel.domain.KeywordsRepository

@Suppress("unchecked_cast")
class KeywordsFactory(private val repository: KeywordsRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return KeywordsModel(repository) as T
    }
}