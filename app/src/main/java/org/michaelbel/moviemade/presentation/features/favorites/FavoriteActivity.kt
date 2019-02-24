package org.michaelbel.moviemade.presentation.features.favorites

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.utils.EXTRA_ACCOUNT_ID
import org.michaelbel.moviemade.presentation.base.BaseActivity

class FavoriteActivity: BaseActivity() {

    fun getToolbar(): Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val accountId = intent.getIntExtra(EXTRA_ACCOUNT_ID, 0)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.favorites)

        if (savedInstanceState == null) {
            startFragment(FavoritesFragment.newInstance(accountId), R.id.fragment_view)
        }
    }
}