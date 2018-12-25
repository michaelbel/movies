package org.michaelbel.moviemade.ui.modules.trailers.adapter

import android.view.View
import org.michaelbel.moviemade.data.entity.Video

interface OnTrailerClickListener {
    fun onTrailerClick(video: Video, view: View)
    fun onTrailerLongClick(video: Video, view: View) : Boolean
}