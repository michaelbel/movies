package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.base.BaseActivity

class SettingsActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.settings)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(container.id, SettingsFragment()).commit()
        }
    }
}

// GA test.
//App.get(this).tracker.send(HitBuilders.EventBuilder().setCategory("Action").setAction("Settings Activity visited").build())