package org.michaelbel.moviemade.presentation.features.search

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.main.MainActivity
import shortbread.Shortcut

@Shortcut(id = "search", rank = 1, icon = R.drawable.ic_shortcut_search, shortLabelRes = R.string.search,
        backStack = [MainActivity::class])
class SearchActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {finish()}

        if (savedInstanceState == null) {
            startFragment(SearchMoviesFragment.newInstance(), container.id)
        }
    }
}