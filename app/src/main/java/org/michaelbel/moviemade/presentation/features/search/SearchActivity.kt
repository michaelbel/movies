package org.michaelbel.moviemade.presentation.features.search

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_container.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.common.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import shortbread.Shortcut

@Shortcut(id = "search", rank = 1, icon = R.drawable.ic_shortcut_search,
        shortLabelRes = R.string.search, backStack = [MainActivity::class])
class SearchActivity: BaseActivity() {

    companion object {
        const val EXTRA_QUERY = "query"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        val query = if (intent.getStringExtra(EXTRA_QUERY) != null) intent.getStringExtra(EXTRA_QUERY) else ""

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(container.id, SearchMoviesFragment.newInstance(query))
                    .commit()
        }
    }
}