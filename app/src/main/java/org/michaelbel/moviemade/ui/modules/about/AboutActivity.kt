package org.michaelbel.moviemade.ui.modules.about

import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_title.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.about.fragments.AboutFragment

class AboutActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    fun getToolbarTitle() : AppCompatTextView {
        return toolbar_title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_title)

        if (savedInstanceState == null) {
            startFragment(AboutFragment(), R.id.fragment_view)
        }
    }
}