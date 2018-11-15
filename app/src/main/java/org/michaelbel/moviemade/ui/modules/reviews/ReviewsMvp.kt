package org.michaelbel.moviemade.ui.modules.reviews

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Review
import org.michaelbel.moviemade.utils.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Video

interface ReviewsMvp : MvpView {

    fun setReviews(reviews: List<Review>)

    fun setError(@EmptyViewMode mode: Int)
}