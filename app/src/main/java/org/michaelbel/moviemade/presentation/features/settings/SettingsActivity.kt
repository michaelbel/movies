package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.base.BaseActivity

class SettingsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.settings)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(container.id, SettingsFragment.newInstance())
                    .commit()
        }
    }
}

// GA test.
//App.get(this).tracker.send(HitBuilders.EventBuilder().setCategory("Action").setAction("Settings Activity visited").build())