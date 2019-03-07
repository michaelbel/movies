package org.michaelbel.moviemade.presentation.features.trailers

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_default.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.entity.Movie
import org.michaelbel.moviemade.core.local.Intents.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.base.BaseActivity

class TrailersActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_default)

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { finish() }
        supportActionBar?.setTitle(R.string.trailers)
        supportActionBar?.subtitle = movie.title

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(container.id, TrailersFragment.newInstance(movie.id)).commit()
        }
    }
}