package org.michaelbel.moviemade.presentation.features.keywords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.data.remote.model.Keyword
import org.michaelbel.domain.KeywordsRepository
import org.michaelbel.moviemade.BuildConfig.TMDB_API_KEY
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_CONNECTION
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_KEYWORDS
import org.michaelbel.moviemade.presentation.listitem.KeywordListItem
import javax.inject.Inject

@HiltViewModel
class KeywordsModel @Inject constructor(val repository: KeywordsRepository): ViewModel() {

    private val itemsManager = Manager()

    var loading = MutableSharedFlow<Boolean>()
    var content = MutableSharedFlow<List<ListItem>>()
    var error = MutableSharedFlow<Int>()
    var click = MutableSharedFlow<Keyword>()
    var longClick = MutableSharedFlow<Keyword>()

    fun keywords(movieId: Long) {
        viewModelScope.launch {
            loading.emit(true)

            try {
                val result = repository.keywords(movieId, TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val list = result.body()?.keywords
                        if (list.isNullOrEmpty()) {
                            error.emit(MODE_NO_KEYWORDS)
                        } else {
                            itemsManager.updateTrailers(list)
                            content.emit(itemsManager.get())
                            repository.addAll(movieId, list)
                        }

                        loading.emit(false)
                    } else {
                        error.emit(MODE_NO_KEYWORDS)
                        loading.emit(false)
                    }
                }
            } catch (e: Throwable) {
                error.emit(MODE_NO_CONNECTION)
                loading.emit(false)
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val keywords = mutableListOf<ListItem>()

        fun updateTrailers(items: List<Keyword>) {
            keywords.clear()

            items.forEach {
                val keywordItem = KeywordListItem(it)
                keywordItem.listener = object: KeywordListItem.Listener {
                    override fun onClick(keyword: Keyword) {
                        viewModelScope.launch { click.emit(keyword) }
                    }

                    override fun onLongClick(keyword: Keyword): Boolean {
                        viewModelScope.launch { longClick.emit(keyword) }
                        return true
                    }
                }
                keywords.add(keywordItem)
            }
        }

        override fun getItems(): List<ListItem> {
            return keywords
        }
    }
}