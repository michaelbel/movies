package org.michaelbel.moviemade.ui.modules.favorites

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_favorite.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.utils.ACCOUNT_ID

class FavoriteActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val accountId = intent.getIntExtra(ACCOUNT_ID, 0)

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }

        if (savedInstanceState == null) {
            startFragment(FavoritesFragment.newInstance(accountId), R.id.fragment_view)
        }
    }
}