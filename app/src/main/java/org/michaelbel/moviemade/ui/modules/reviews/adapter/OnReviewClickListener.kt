package org.michaelbel.moviemade.ui.modules.reviews.adapter

import android.view.View
import org.michaelbel.moviemade.data.entity.Review

interface OnReviewClickListener {
    fun onReviewClick(review: Review, view: View)
}