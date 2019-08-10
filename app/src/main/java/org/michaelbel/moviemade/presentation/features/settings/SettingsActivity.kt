package org.michaelbel.moviemade.presentation.features.settings

import android.os.Bundle
import androidx.fragment.app.transaction
import kotlinx.android.synthetic.main.activity_settings.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.base.BaseActivity

class SettingsActivity: BaseActivity(R.layout.activity_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.settings)

        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                replace(container.id, SettingsFragment.newInstance())
            }
        }
    }
}