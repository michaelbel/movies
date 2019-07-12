package org.michaelbel.moviemade.presentation.features.search

import android.os.Bundle
import androidx.fragment.app.transaction
import kotlinx.android.synthetic.main.activity_parent.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.base.BaseActivity

class SearchActivity: BaseActivity() {

    companion object {
        const val EXTRA_QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        val query = if (intent.getStringExtra(EXTRA_QUERY) != null) intent.getStringExtra(EXTRA_QUERY) else ""

        if (savedInstanceState == null) {
            supportFragmentManager.transaction {
                replace(container.id, SearchMoviesFragment.newInstance(query))
            }
        }
    }
}