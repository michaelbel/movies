package org.michaelbel.moviemade.ui.modules.reviews

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Review
import org.michaelbel.moviemade.utils.EmptyViewMode

interface ReviewsMvp : MvpView {

    fun setReviews(reviews: List<Review>)

    fun setError(@EmptyViewMode mode: Int)
}