package org.michaelbel.moviemade.presentation.features.watchlist

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.presentation.base.BaseActivity

class WatchlistActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, 0)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.watchlist)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(container.id, WatchlistFragment.newInstance(accountId)).commit()
        }
    }
}