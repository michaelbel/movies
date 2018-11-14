package org.michaelbel.moviemade.ui.modules.reviews

import com.arellomobile.mvp.MvpView

import org.michaelbel.moviemade.annotation.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Review

interface ReviewsMvp : MvpView {

    fun setReviews(reviews: List<Review>, firstPage: Boolean)

    fun setError(@EmptyViewMode mode: Int)
}