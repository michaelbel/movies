package org.michaelbel.moviemade.presentation.features.keywords.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Keyword
import org.michaelbel.moviemade.core.local.Intents.EXTRA_KEYWORD
import org.michaelbel.moviemade.presentation.base.BaseActivity
import org.michaelbel.moviemade.presentation.features.keywords.fragment.KeywordFragment

class KeywordActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val keyword = intent.getSerializableExtra(EXTRA_KEYWORD) as Keyword

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.title = keyword.name

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(container.id, KeywordFragment.newInstance(keyword.id))
                    .commit()
        }
    }
}