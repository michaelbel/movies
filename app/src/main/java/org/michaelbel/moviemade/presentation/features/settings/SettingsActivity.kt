package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.base.BaseActivity

class SettingsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        initToolbar()

        if (savedInstanceState == null) {
            startFragment(SettingsFragment(), R.id.fragment_view)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.settings)
    }
}

// GA test.
//App.get(this).tracker.send(HitBuilders.EventBuilder().setCategory("Action").setAction("Settings Activity visited").build())