package org.michaelbel.moviemade.presentation.features.sources

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.presentation.listitem.TextDetailListItem

class SourcesModel: ViewModel() {

    var items = MutableLiveData<ArrayList<ListItem>>()
    var click = MutableLiveData<LiveDataEvent<String>>()

    fun init(items: List<SourcesFragment.Source>) {
        val list = ArrayList<ListItem>()

        for (item in items) {
            val listItem = TextDetailListItem(TextDetailListItem.Data(item.name, item.license, item != items[items.size - 1]))
            listItem.listener = object: TextDetailListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(item.url))
                }
            }
            list.add(listItem)
        }

        this.items.postValue(list)
    }
}