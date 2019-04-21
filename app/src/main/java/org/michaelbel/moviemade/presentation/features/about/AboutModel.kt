package org.michaelbel.moviemade.presentation.features.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.michaelbel.core.adapter.ListItem
import org.michaelbel.domain.live.LiveDataEvent
import org.michaelbel.moviemade.BuildConfig.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.App
import org.michaelbel.moviemade.presentation.listitem.AboutListItem
import org.michaelbel.moviemade.presentation.listitem.PoweredListItem
import org.michaelbel.moviemade.presentation.listitem.TextListItem

class AboutModel(app: Application): AndroidViewModel(app) {

    var items = MutableLiveData<ArrayList<ListItem>>()
    var click = MutableLiveData<LiveDataEvent<String>>()

    init {
        val context = getApplication<App>()
        val list = ArrayList<ListItem>()

        list.add(AboutListItem(AboutListItem.Data(
                context.getString(R.string.app_for_android, context.getString(R.string.app_name)),
                context.getString(R.string.version_build_date, VERSION_NAME, VERSION_CODE, VERSION_DATE)
        )))

        val rate = TextListItem(TextListItem.Data(R.drawable.ic_google_play, R.string.rate_google_play))
        rate.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("rate"))
            }
        }
        list.add(rate)

        val fork = TextListItem(TextListItem.Data(R.drawable.ic_github, R.string.fork_github))
        fork.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("fork"))
            }
        }
        list.add(fork)

        val libs = TextListItem(TextListItem.Data(R.drawable.ic_storage, R.string.open_source_libs))
        libs.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("libs"))
            }
        }
        list.add(libs)

        val apps = TextListItem(TextListItem.Data(R.drawable.ic_shop, R.string.other_developer_apps))
        apps.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("apps"))
            }
        }
        list.add(apps)

        val feedback = TextListItem(TextListItem.Data(R.drawable.ic_mail, R.string.feedback))
        feedback.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("feedback"))
            }
        }
        list.add(feedback)

        val share = TextListItem(TextListItem.Data(R.drawable.ic_share, R.string.share_with_friends))
        share.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("share"))
            }
        }
        list.add(share)

        val donate = TextListItem(TextListItem.Data(R.drawable.ic_paypal, R.string.donate_paypal))
        donate.listener = object: TextListItem.Listener {
            override fun onClick() {
                click.postValue(LiveDataEvent("donate"))
            }
        }
        list.add(donate)

        list.add(PoweredListItem(PoweredListItem.Data(R.string.powered_by)))

        items.postValue(list)
    }
}