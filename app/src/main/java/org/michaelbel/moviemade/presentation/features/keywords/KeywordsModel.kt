package org.michaelbel.moviemade.presentation.features.keywords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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

class KeywordsModel(val repository: KeywordsRepository): ViewModel() {

    private val itemsManager = Manager()

    var loading = MutableLiveData<Boolean>()
    var content = MutableLiveData<ArrayList<ListItem>>()
    var error = MutableLiveData<Int>()
    var click = MutableLiveData<Keyword>()
    var longClick = MutableLiveData<Keyword>()

    fun keywords(movieId: Long) {
        loading.postValue(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = repository.keywords(movieId, TMDB_API_KEY)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        val list = result.body()?.keywords
                        if (list.isNullOrEmpty()) {
                            error.postValue(MODE_NO_KEYWORDS)
                        } else {
                            itemsManager.updateTrailers(list)
                            content.postValue(itemsManager.get())
                            repository.addAll(movieId, list)
                        }

                        loading.postValue(false)
                    } else {
                        error.postValue(MODE_NO_KEYWORDS)
                        loading.postValue(false)
                    }
                }
            } catch (e: Throwable) {
                error.postValue(MODE_NO_CONNECTION)
                loading.postValue(false)
            }
        }
    }

    private inner class Manager: ItemsManager() {

        private val keywords = ArrayList<ListItem>()

        fun updateTrailers(items: List<Keyword>) {
            keywords.clear()

            items.forEach {
                val keywordItem = KeywordListItem(it)
                keywordItem.listener = object: KeywordListItem.Listener {
                    override fun onClick(keyword: Keyword) {
                        click.postValue(keyword)
                    }

                    override fun onLongClick(keyword: Keyword): Boolean {
                        longClick.postValue(keyword)
                        return true
                    }
                }
                keywords.add(keywordItem)
            }
        }

        override fun getItems(): ArrayList<ListItem> {
            return keywords
        }
    }
}