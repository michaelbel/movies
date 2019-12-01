package org.michaelbel.moviemade.presentation.features.settings

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.SharedPrefs.KEY_IN_APP_BROWSER
import org.michaelbel.moviemade.core.local.SharedPrefs.NAME
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.listitem.TextDetailListItem
import org.michaelbel.moviemade.presentation.listitem.TextListItem

class SettingsModel(app: Application): AndroidViewModel(app) {

    sealed class Keys {
        object About: Keys()
    }

    private val itemsManager = Manager()

    var items = MutableLiveData<ArrayList<ListItem>>()
    var click = MutableLiveData<LiveDataEvent<Keys>>()

    private fun getContext(): Context = getApplication<App>()

    init {
        itemsManager.updateItems()
        items.postValue(itemsManager.get())
    }

    private inner class Manager: ItemsManager() {

        private val items = ArrayList<ListItem>()

        fun updateItems() {
            items.clear()

            val prefs = getContext().getSharedPreferences(NAME, MODE_PRIVATE)

            val inAppBrowserItem = TextDetailListItem(TextDetailListItem.Data(getContext().getString(R.string.in_app_browser), getContext().getString(R.string.in_app_browser_summary), prefs.getBoolean(KEY_IN_APP_BROWSER, false), true))
            inAppBrowserItem.listener = object: TextDetailListItem.Listener {
                override fun onChecked(checked: Boolean) {
                    prefs.edit { putBoolean(KEY_IN_APP_BROWSER, checked) }
                }
            }
            items.add(inAppBrowserItem)

            val aboutListItem = TextListItem(TextListItem.Data(text = R.string.about, divider = false))
            aboutListItem.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.About))
                }
            }
            items.add(aboutListItem)
        }

        override fun getItems(): ArrayList<ListItem> = items
    }
}