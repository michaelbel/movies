package org.michaelbel.moviemade.ui.modules.keywords

import com.arellomobile.mvp.MvpView
import org.michaelbel.moviemade.data.dao.Keyword
import org.michaelbel.moviemade.data.dao.Movie
import org.michaelbel.moviemade.data.dao.Review
import org.michaelbel.moviemade.utils.EmptyViewMode
import org.michaelbel.moviemade.data.dao.Video

interface KeywordsMvp : MvpView {

    fun setKeywords(keywords: List<Keyword>)

    fun setError(@EmptyViewMode mode: Int)
}