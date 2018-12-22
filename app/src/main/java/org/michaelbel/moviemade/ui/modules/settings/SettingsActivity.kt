package org.michaelbel.moviemade.ui.modules.settings

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.activity_settings.*
import org.michaelbel.moviemade.Moviemade
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity

class SettingsActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    fun getToolbarTitle() : AppCompatTextView {
        return toolbar_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            startFragment(SettingsFragment(), R.id.fragment_view)
        }

        Moviemade.get(this).tracker.send(HitBuilders.EventBuilder().setCategory("Action").setAction("Settings Activity visited").build())
    }
}