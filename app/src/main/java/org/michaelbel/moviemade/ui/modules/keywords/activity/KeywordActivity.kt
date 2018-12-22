package org.michaelbel.moviemade.ui.modules.keywords.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_keyword.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.data.entity.Keyword
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordFragment
import org.michaelbel.moviemade.utils.KEYWORD

class KeywordActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        val keyword = intent.getSerializableExtra(KEYWORD) as Keyword

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar_title!!.text = keyword.name

        if (savedInstanceState == null) {
            startFragment(KeywordFragment.newInstance(keyword.id), R.id.fragment_view)
        }
    }
}