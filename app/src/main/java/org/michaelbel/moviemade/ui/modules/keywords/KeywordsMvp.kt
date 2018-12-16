package org.michaelbel.moviemade.ui.modules.keywords

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.entity.Keyword
import org.michaelbel.moviemade.utils.EmptyViewMode

interface KeywordsMvp : MvpView {

    fun setKeywords(keywords: List<Keyword>)

    fun setError(@EmptyViewMode mode: Int)
}