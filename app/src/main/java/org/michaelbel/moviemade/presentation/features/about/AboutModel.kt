package org.michaelbel.moviemade.presentation.features.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ItemsManager
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.listitem.AboutListItem
import org.michaelbel.moviemade.presentation.listitem.AppUpdateListItem
import org.michaelbel.moviemade.presentation.listitem.PoweredListItem
import org.michaelbel.moviemade.presentation.listitem.TextListItem
import javax.inject.Inject

@HiltViewModel
class AboutModel @Inject constructor(): ViewModel() {

    var items = MutableSharedFlow<List<ListItem>>()
    var click = MutableSharedFlow<Keys>()

    private val itemsManager = Manager()

    // IAUs
    private var updateItemVisibility: Boolean = false
    var appUpdateManager: AppUpdateManager? = null
    var appUpdateInfo: Task<AppUpdateInfo>? = null

    init {
        viewModelScope.launch {
            itemsManager.updateItems()
            items.emit(itemsManager.get())
        }

        checkNewAppVersion()
    }

    private fun checkNewAppVersion() {
        /*appUpdateManager = AppUpdateManagerFactory.create(getContext())

        appUpdateInfo = appUpdateManager?.appUpdateInfo
        appUpdateInfo?.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                updateItemVisibility = true
                items.postValue(itemsManager.get())
            }
        }
        appUpdateInfo?.addOnFailureListener { Timber.e(it) }*/
    }

    private inner class Manager: ItemsManager() {

        private val items = ArrayList<ListItem>()

        fun updateItems() {
            items.clear()

            items.add(AboutListItem())

            if (updateItemVisibility) {
                val update = AppUpdateListItem(AppUpdateListItem.Data())
                update.listener = object: AppUpdateListItem.Listener {
                    override fun onClick() {
                        viewModelScope.launch { click.emit(Keys.Update) }
                    }
                }
                items.add(1, update)
            }

            val rate = TextListItem(TextListItem.Data(R.drawable.ic_google_play, R.string.rate_google_play))
            rate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Rate) }
                }
            }
            items.add(rate)

            val fork = TextListItem(TextListItem.Data(R.drawable.ic_github, R.string.fork_github))
            fork.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Fork) }
                }
            }
            items.add(fork)

            val libs = TextListItem(TextListItem.Data(R.drawable.ic_storage, R.string.open_source_libs))
            libs.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Libs) }
                }
            }
            items.add(libs)

            val apps = TextListItem(TextListItem.Data(R.drawable.ic_shop, R.string.other_developer_apps))
            apps.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Apps) }
                }
            }
            items.add(apps)

            val feedback = TextListItem(TextListItem.Data(R.drawable.ic_mail, R.string.feedback))
            feedback.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Feedback) }
                }
            }
            items.add(feedback)

            val share = TextListItem(TextListItem.Data(R.drawable.ic_share, R.string.share_with_friends))
            share.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Share) }
                }
            }
            items.add(share)

            val donate = TextListItem(TextListItem.Data(R.drawable.ic_paypal, R.string.donate_paypal))
            donate.listener = object: TextListItem.Listener {
                override fun onClick() {
                    viewModelScope.launch { click.emit(Keys.Donate) }
                }
            }
            items.add(donate)
            items.add(PoweredListItem(PoweredListItem.Data(R.string.powered_by)))
        }

        override fun getItems(): ArrayList<ListItem> = items
    }

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
}