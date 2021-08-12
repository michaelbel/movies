package org.michaelbel.moviemade.presentation.features.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.listitem.TextListItem
import javax.inject.Inject

@HiltViewModel
class SettingsModel @Inject constructor(
    private val preferences: SharedPreferences
): ViewModel() {

    sealed class Keys {
        object About: Keys()
    }

    private val itemsManager = Manager()

    var items = MutableSharedFlow<List<ListItem>>()
    var click = MutableSharedFlow<Keys>()

    init {
        viewModelScope.launch {
            itemsManager.updateItems()
            items.emit(itemsManager.get())
        }
    }

    private inner class Manager: ItemsManager() {

        private val items = ArrayList<ListItem>()

        fun updateItems() {
            items.clear()

            /*val inAppBrowserItem = TextDetailListItem(TextDetailListItem.Data(getContext().getString(R.string.in_app_browser), getContext().getString(R.string.in_app_browser_summary), prefs.getBoolean(KEY_IN_APP_BROWSER, false), true))
            inAppBrowserItem.listener = object: TextDetailListItem.Listener {
                override fun onChecked(checked: Boolean) {
                    sharedPreferences.edit { putBoolean(KEY_IN_APP_BROWSER, checked) }
                }
            }
            items.add(inAppBrowserItem)*/

            val aboutListItem = TextListItem(TextListItem.Data(text = R.string.about, divider = false))
            aboutListItem.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.About) }
                }
            }
            items.add(aboutListItem)
        }

        override fun getItems(): ArrayList<ListItem> = items
    }
}