package org.michaelbel.moviemade.presentation.features.about

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.listitem.AboutListItem
import org.michaelbel.moviemade.presentation.listitem.AppUpdateListItem
import org.michaelbel.moviemade.presentation.listitem.PoweredListItem
import org.michaelbel.moviemade.presentation.listitem.TextListItem

class AboutModel(app: Application): AndroidViewModel(app) {

    private val itemsManager = Manager()

    var items = MutableLiveData<ArrayList<ListItem>>()
    var click = MutableLiveData<LiveDataEvent<String>>()

    init {
        itemsManager.updateItems()
        items.postValue(itemsManager.get())
    }

    private fun getContext(): Context {
        return getApplication<App>()
    }

    fun addAppUpdateItem() {
        itemsManager.updateAppUpdateItem()
        items.postValue(itemsManager.get())
    }

    private inner class Manager: ItemsManager() {

        private val items = ArrayList<ListItem>()

        fun updateItems() {
            items.clear()

            items.add(AboutListItem(AboutListItem.Data(
                    getContext().getString(R.string.app_for_android, getContext().getString(R.string.app_name)),
                    getContext().getString(R.string.version_build_date, VERSION_NAME, VERSION_CODE, VERSION_DATE)
            )))

            val rate = TextListItem(TextListItem.Data(R.drawable.ic_google_play, R.string.rate_google_play))
            rate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("rate"))
                }
            }
            items.add(rate)

            val fork = TextListItem(TextListItem.Data(R.drawable.ic_github, R.string.fork_github))
            fork.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("fork"))
                }
            }
            items.add(fork)

            val libs = TextListItem(TextListItem.Data(R.drawable.ic_storage, R.string.open_source_libs))
            libs.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("libs"))
                }
            }
            items.add(libs)

            val apps = TextListItem(TextListItem.Data(R.drawable.ic_shop, R.string.other_developer_apps))
            apps.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("apps"))
                }
            }
            items.add(apps)

            val feedback = TextListItem(TextListItem.Data(R.drawable.ic_mail, R.string.feedback))
            feedback.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("feedback"))
                }
            }
            items.add(feedback)

            val share = TextListItem(TextListItem.Data(R.drawable.ic_share, R.string.share_with_friends))
            share.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("share"))
                }
            }
            items.add(share)

            val donate = TextListItem(TextListItem.Data(R.drawable.ic_paypal, R.string.donate_paypal))
            donate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("donate"))
                }
            }
            items.add(donate)
            items.add(PoweredListItem(PoweredListItem.Data(R.string.powered_by)))
        }

        fun updateAppUpdateItem() {
            val update = AppUpdateListItem(AppUpdateListItem.Data(object: AppUpdateListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent("update"))
                }
            }))
            items.add(1, update)
        }

        override fun getItems(): ArrayList<ListItem> {
            return items
        }
    }
}