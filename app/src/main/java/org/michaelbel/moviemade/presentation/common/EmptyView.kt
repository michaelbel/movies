package org.michaelbel.moviemade.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import kotlinx.android.synthetic.main.view_empty.view.*
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.ViewUtil
import org.michaelbel.moviemade.core.errors.EmptyViewMode

class EmptyView: FrameLayout {

    constructor(context: Context): super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        initialize()
    }

    private fun initialize() {
        LayoutInflater.from(context).inflate(R.layout.view_empty, this, true)
    }

    fun setMode(@EmptyViewMode mode: Int): EmptyView {
        when (mode) {
            EmptyViewMode.MODE_NO_CONNECTION -> {
                setIcon(R.drawable.ic_offline)
                setText(R.string.error_offline)
            }
            EmptyViewMode.MODE_NO_MOVIES -> {
                setIcon(R.drawable.ic_movie_roll)
                setText(R.string.no_movies)
            }
            EmptyViewMode.MODE_NO_PEOPLE -> {
                setIcon(R.drawable.ic_account_circle)
                setText(R.string.no_people)
            }
            EmptyViewMode.MODE_NO_REVIEWS -> {
                setIcon(R.drawable.ic_review)
                setText(R.string.no_reviews)
            }
            EmptyViewMode.MODE_NO_RESULTS -> {
                setIcon(R.drawable.ic_database_search)
                setText(R.string.no_results)
            }
            EmptyViewMode.MODE_NO_TRAILERS -> {
                setIcon(R.drawable.ic_trailer)
                setText(R.string.no_trailers)
            }
            EmptyViewMode.MODE_NO_KEYWORDS -> {
                setIcon(R.drawable.ic_card_text)
                setText(R.string.no_keywords)
            }
        }

        return this
    }

    private fun setIcon(icon: Int) {
        emptyIcon.setImageDrawable(ViewUtil.getIcon(context, icon, R.color.errorColor))
    }

    private fun setText(@StringRes textId: Int) {
        emptyText.text = context.getString(textId)
    }

    fun setValue(@StringRes textId: Int) {
        value.visibility = VISIBLE
        value.text = context.getString(textId)
    }
}