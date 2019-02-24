package org.michaelbel.moviemade.presentation.features.about

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.ViewUtil
import org.michaelbel.moviemade.presentation.base.BaseActivity

class AboutActivity: BaseActivity() {

    fun getToolbar(): Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(ViewUtil.getAttrColor(this, android.R.attr.colorBackground))
        ViewUtil.setElevation(appbar, 0F)

        if (savedInstanceState == null) {
            startFragment(AboutFragment(), R.id.fragment_view)
        }
    }
}