package org.michaelbel.moviemade.ui.modules.similar

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_similar.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.ui.base.BaseActivity
import org.michaelbel.moviemade.utils.MOVIE

class SimilarMoviesActivity : BaseActivity() {

    fun getToolbar() : Toolbar {
        return toolbar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar)

        val movie = intent.getSerializableExtra(MOVIE) as Movie

        setSupportActionBar(toolbar)
        toolbar!!.setNavigationOnClickListener { finish() }
        toolbar_subtitle!!.text = title

        if (savedInstanceState == null) {
            startFragment(SimilarMoviesFragment.newInstance(movie.id), R.id.fragment_view)
        }
    }
}