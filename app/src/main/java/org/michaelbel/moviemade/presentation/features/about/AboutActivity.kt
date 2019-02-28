package org.michaelbel.moviemade.presentation.features.about

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.ViewUtil
import org.michaelbel.moviemade.presentation.base.BaseActivity

class AboutActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ViewUtil.getAttrColor(this, android.R.attr.colorBackground))
        ViewUtil.setElevation(appbar, 0F)

        if (savedInstanceState == null) {
            startFragment(AboutFragment(), container.id)
        }
    }
}