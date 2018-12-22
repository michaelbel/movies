package org.michaelbel.moviemade.ui.modules.watchlist

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_watchlist.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.utils.ACCOUNT_ID

class WatchlistActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watchlist)

        val accountId = intent.getIntExtra(ACCOUNT_ID, 0)

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }

        if (savedInstanceState == null) {
            startFragment(WatchlistFragment.newInstance(accountId), R.id.fragment_view)
        }
    }
}