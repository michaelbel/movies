package org.michaelbel.moviemade.ui.base

import android.content.Intent
import android.content.SharedPreferences
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import org.michaelbel.moviemade.data.entity.Keyword
import org.michaelbel.moviemade.data.entity.Movie
import org.michaelbel.moviemade.data.entity.Review
import org.michaelbel.moviemade.ui.modules.favorites.FavoriteActivity
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordActivity
import org.michaelbel.moviemade.ui.modules.keywords.activity.KeywordsActivity
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity
import org.michaelbel.moviemade.ui.modules.recommendations.RcmdMoviesActivity
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewActivity
import org.michaelbel.moviemade.ui.modules.reviews.activity.ReviewsActivity
import org.michaelbel.moviemade.ui.modules.similar.SimilarMoviesActivity
import org.michaelbel.moviemade.ui.modules.trailers.TrailersActivity
import org.michaelbel.moviemade.ui.modules.watchlist.WatchlistActivity
import org.michaelbel.moviemade.utils.*

abstract class BaseActivity : AppCompatActivity(), BaseContract, BaseContract.BaseView, BaseContract.MediaView {

    private var unbinder: Unbinder? = null

    override fun setContentView(layoutId: Int) {
        super.setContentView(layoutId)
        unbinder = ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbinder!!.unbind()
    }

//--------------------------------------------------------------------------------------------------

    override fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(SP_NAME, MODE_PRIVATE)
    }

//--BaseView----------------------------------------------------------------------------------------

    override fun startFragment(fragment: Fragment, container: View) {
        supportFragmentManager.beginTransaction().replace(container.id, fragment).commit()
    }

    override fun startFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction().replace(containerId, fragment).commit()
    }

    override fun startFragment(fragment: Fragment, container: View, tag: String) {
        supportFragmentManager.beginTransaction().replace(container.id, fragment).addToBackStack(tag).commit()
    }

    override fun startFragment(fragment: Fragment, containerId: Int, tag: String) {
        supportFragmentManager.beginTransaction().replace(containerId, fragment).addToBackStack(tag).commit()
    }

    override fun finishFragment() {
        supportFragmentManager.popBackStack()
    }

//--MediaView---------------------------------------------------------------------------------------

    override fun startMovie(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun startTrailers(movie: Movie) {
        val intent = Intent(this, TrailersActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun startReview(review: Review, movie: Movie) {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra(MOVIE, movie)
        intent.putExtra(REVIEW, review)
        startActivity(intent)
    }

    override fun startReviews(movie: Movie) {
        val intent = Intent(this, ReviewsActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun startFavorites(accountId: Int) {
        val intent = Intent(this, FavoriteActivity::class.java)
        intent.putExtra(ACCOUNT_ID, accountId)
        startActivity(intent)
    }

    override fun startWatchlist(accountId: Int) {
        val intent = Intent(this, WatchlistActivity::class.java)
        intent.putExtra(ACCOUNT_ID, accountId)
        startActivity(intent)
    }

    override fun startKeywords(movie: Movie) {
        val intent = Intent(this, KeywordsActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun startKeyword(keyword: Keyword) {
        val intent = Intent(this, KeywordActivity::class.java)
        intent.putExtra(KEYWORD, keyword)
        startActivity(intent)
    }

    override fun startSimilarMovies(movie: Movie) {
        val intent = Intent(this, SimilarMoviesActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }

    override fun startRcmdsMovies(movie: Movie) {
        val intent = Intent(this, RcmdMoviesActivity::class.java)
        intent.putExtra(MOVIE, movie)
        startActivity(intent)
    }
}