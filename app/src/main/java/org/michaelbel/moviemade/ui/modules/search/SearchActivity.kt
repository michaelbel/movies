package org.michaelbel.moviemade.ui.modules.search

import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import kotlinx.android.synthetic.main.activity_search.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.main.MainActivity
import org.michaelbel.moviemade.utils.QUERY
import shortbread.Shortcut

@Shortcut(id = "search", rank = 1, icon = R.drawable.ic_shortcut_search, shortLabelRes = R.string.search, backStack = [MainActivity::class])
class SearchActivity : BaseActivity() {

    fun getSearchEditText() : AppCompatEditText {
        return search_edit_text
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val query = intent.getStringExtra(QUERY)

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener {finish()}

        if (savedInstanceState == null) {
            startFragment(SearchMoviesFragment.newInstance(query), R.id.fragment_view)
        }
    }
}