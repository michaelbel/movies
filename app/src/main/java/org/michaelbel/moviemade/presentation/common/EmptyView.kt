package org.michaelbel.moviemade.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.state.EmptyState
import org.michaelbel.moviemade.databinding.ViewEmptyBinding
import org.michaelbel.moviemade.ktx.setIcon

class EmptyView(context: Context, attrs: AttributeSet?): FrameLayout(context, attrs) {

    private val binding = ViewEmptyBinding.inflate(LayoutInflater.from(context), this, true)

    fun setMode(mode: Int): EmptyView {
        when (mode) {
            EmptyState.MODE_NO_CONNECTION -> {
                setIcon(R.drawable.ic_offline)
                setText(R.string.error_offline)
            }
            EmptyState.MODE_NO_MOVIES -> {
                setIcon(R.drawable.ic_movie_roll)
                setText(R.string.no_movies)
            }
            EmptyState.MODE_NO_PEOPLE -> {
                setIcon(R.drawable.ic_account_circle)
                setText(R.string.no_people)
            }
            EmptyState.MODE_NO_REVIEWS -> {
                setIcon(R.drawable.ic_review)
                setText(R.string.no_reviews)
            }
            EmptyState.MODE_NO_RESULTS -> {
                setIcon(R.drawable.ic_database_search)
                setText(R.string.no_results)
            }
            EmptyState.MODE_NO_TRAILERS -> {
                setIcon(R.drawable.ic_trailer)
                setText(R.string.no_trailers)
            }
            EmptyState.MODE_NO_KEYWORDS -> {
                setIcon(R.drawable.ic_card_text)
                setText(R.string.no_keywords)
            }
        }

        return this
    }

    private fun setIcon(icon: Int) {
        binding.emptyIcon.setIcon(icon, R.color.errorColor)
    }

    private fun setText(@StringRes textId: Int) {
        binding.emptyText.text = context.getString(textId)
    }

    fun setValue(@StringRes textId: Int) {
        binding.summary.isVisible = true
        binding.summary.text = context.getString(textId)
    }
}