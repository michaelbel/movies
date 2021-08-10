package org.michaelbel.moviemade.presentation.features.about

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.time.DateUtil
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.listitem.AboutListItem
import org.michaelbel.moviemade.presentation.listitem.AppUpdateListItem
import org.michaelbel.moviemade.presentation.listitem.PoweredListItem
import org.michaelbel.moviemade.presentation.listitem.TextListItem
import timber.log.Timber

class AboutModel(app: Application): AndroidViewModel(app) {

    sealed class Keys {
        object Rate: Keys()
        object Fork: Keys()
        object Libs: Keys()
        object Apps: Keys()
        object Feedback: Keys()
        object Share: Keys()
        object Donate: Keys()
        object Update: Keys()
    }

    private val itemsManager = Manager()

    var items = MutableLiveData<ArrayList<ListItem>>()
    var click = MutableLiveData<LiveDataEvent<Keys>>()

    // IAUs
    private var updateItemVisibility: Boolean = false
    var appUpdateManager: AppUpdateManager? = null
    var appUpdateInfo: Task<AppUpdateInfo>? = null

    private fun getContext(): Context = getApplication<App>()

    init {
        itemsManager.updateItems()
        items.postValue(itemsManager.get())

        checkNewAppVersion()
    }

    private fun checkNewAppVersion() {
        appUpdateManager = AppUpdateManagerFactory.create(getContext())

        appUpdateInfo = appUpdateManager?.appUpdateInfo
        appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                updateItemVisibility = true
                items.postValue(itemsManager.get())
            }
        }
        appUpdateInfo?.addOnFailureListener { Timber.e(it) }
    }

    private inner class Manager: ItemsManager() {

        private val items = ArrayList<ListItem>()

        fun updateItems() {
            items.clear()

            items.add(AboutListItem(AboutListItem.Data(
                    getContext().getString(R.string.app_for_android, getContext().getString(R.string.app_name)),
                    getContext().getString(R.string.version_build_date, VERSION_NAME, VERSION_CODE, DateUtil.formatSystemTime(VERSION_DATE))
            )))

            if (updateItemVisibility) {
                val update = AppUpdateListItem(AppUpdateListItem.Data())
                update.listener = object: AppUpdateListItem.Listener {
                    override fun onClick() {
                        click.postValue(LiveDataEvent(Keys.Update))
                    }
                }
                items.add(1, update)
            }

            val rate = TextListItem(TextListItem.Data(R.drawable.ic_google_play, R.string.rate_google_play))
            rate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Rate))
                }
            }
            items.add(rate)

            val fork = TextListItem(TextListItem.Data(R.drawable.ic_github, R.string.fork_github))
            fork.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Fork))
                }
            }
            items.add(fork)

            val libs = TextListItem(TextListItem.Data(R.drawable.ic_storage, R.string.open_source_libs))
            libs.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Libs))
                }
            }
            items.add(libs)

            val apps = TextListItem(TextListItem.Data(R.drawable.ic_shop, R.string.other_developer_apps))
            apps.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Apps))
                }
            }
            items.add(apps)

            val feedback = TextListItem(TextListItem.Data(R.drawable.ic_mail, R.string.feedback))
            feedback.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Feedback))
                }
            }
            items.add(feedback)

            val share = TextListItem(TextListItem.Data(R.drawable.ic_share, R.string.share_with_friends))
            share.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Share))
                }
            }
            items.add(share)

            val donate = TextListItem(TextListItem.Data(R.drawable.ic_paypal, R.string.donate_paypal))
            donate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    click.postValue(LiveDataEvent(Keys.Donate))
                }
            }
            items.add(donate)
            items.add(PoweredListItem(PoweredListItem.Data(R.string.powered_by)))
        }

        override fun getItems(): ArrayList<ListItem> = items
    }
}