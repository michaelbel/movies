package org.michaelbel.moviemade.utils

import org.michaelbel.moviemade.Moviemade

object AndroidUtil {

    fun runOnUIThread(runnable: Runnable, delay: Long) {
        if (delay == 0L) {
            Moviemade.appHandler.post(runnable)
        } else {
            Moviemade.appHandler.postDelayed(runnable, delay)
        }
    }

    fun cancelRunOnUIThread(runnable: Runnable) {
        Moviemade.appHandler.removeCallbacks(runnable)
    }
}