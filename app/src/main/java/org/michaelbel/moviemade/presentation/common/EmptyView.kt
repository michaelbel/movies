package org.michaelbel.moviemade.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.view_empty.view.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_CONNECTION
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_KEYWORDS
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_MOVIES
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_PEOPLE
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_RESULTS
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_REVIEWS
import org.michaelbel.moviemade.core.state.EmptyState.MODE_NO_TRAILERS
import org.michaelbel.moviemade.ktx.inflate
import org.michaelbel.moviemade.ktx.setIcon
import org.michaelbel.moviemade.ktx.visible

class EmptyView(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs) {

    init { inflate(R.layout.view_empty) }

    fun setMode(mode: Int): EmptyView {
        when (mode) {
            MODE_NO_CONNECTION -> {
                setIcon(R.drawable.ic_offline)
                setText(R.string.error_offline)
            }
            MODE_NO_MOVIES -> {
                setIcon(R.drawable.ic_movie_roll)
                setText(R.string.no_movies)
            }
            MODE_NO_PEOPLE -> {
                setIcon(R.drawable.ic_account_circle)
                setText(R.string.no_people)
            }
            MODE_NO_REVIEWS -> {
                setIcon(R.drawable.ic_review)
                setText(R.string.no_reviews)
            }
            MODE_NO_RESULTS -> {
                setIcon(R.drawable.ic_database_search)
                setText(R.string.no_results)
            }
            MODE_NO_TRAILERS -> {
                setIcon(R.drawable.ic_trailer)
                setText(R.string.no_trailers)
            }
            MODE_NO_KEYWORDS -> {
                setIcon(R.drawable.ic_card_text)
                setText(R.string.no_keywords)
            }
        }

        return this
    }

    private fun setIcon(icon: Int) {
        emptyIcon.setIcon(icon, R.color.errorColor)
    }

    private fun setText(@StringRes textId: Int) {
        emptyText.text = context.getString(textId)
    }

    fun setValue(@StringRes textId: Int) {
        summary.visible()
        summary.text = context.getString(textId)
    }
}