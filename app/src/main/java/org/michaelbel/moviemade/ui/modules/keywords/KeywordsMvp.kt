package org.michaelbel.moviemade.ui.modules.keywords

import com.arellomobile.mvp.MvpView

import org.michaelbel.moviemade.annotation.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Keyword

interface KeywordsMvp : MvpView {

    fun setKeywords(keywords: List<Keyword>)

    fun setError(@EmptyViewMode mode: Int)
}