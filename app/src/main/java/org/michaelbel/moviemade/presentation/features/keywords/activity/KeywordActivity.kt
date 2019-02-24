package org.michaelbel.moviemade.presentation.features.keywords.activity

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_frame.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.utils.KEYWORD
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.keywords.fragment.KeywordFragment

class KeywordActivity: BaseActivity() {

    fun getToolbar(): Toolbar = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)

        val keyword = intent.getSerializableExtra(KEYWORD) as Keyword

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.title = keyword.name

        if (savedInstanceState == null) {
            startFragment(KeywordFragment.newInstance(keyword.id), R.id.fragment_view)
        }
    }
}