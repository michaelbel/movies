package org.michaelbel.moviemade.presentation.features.sources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.moviemade.presentation.listitem.TextDetailListItem
import javax.inject.Inject

@HiltViewModel
class SourcesModel @Inject constructor(): ViewModel() {

    var content = MutableSharedFlow<ArrayList<ListItem>>()
    var click = MutableSharedFlow<String>()

    fun init(items: List<SourcesFragment.Source>) {
        val list = ArrayList<ListItem>()

        items.forEach {
            val listItem = TextDetailListItem(TextDetailListItem.Data(it.name, it.license, divider = it != items[items.size - 1]))
            listItem.listener = object: TextDetailListItem.Listener {
                override fun onClick() {
                    if (it.url != null) {
                        viewModelScope.launch { click.emit(it.url) }
                    }
                }
            }
            list.add(listItem)
        }

        viewModelScope.launch { content.emit(list) }
    }
}