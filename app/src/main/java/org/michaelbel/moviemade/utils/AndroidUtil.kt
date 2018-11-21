package org.michaelbel.moviemade.utils

import org.michaelbel.moviemade.Moviemade

object AndroidUtil {

    fun runOnUIThread(runnable: Runnable, delay: Long) {
        if (delay == 0L) {
            Moviemade.AppHandler.post(runnable)
        } else {
            Moviemade.AppHandler.postDelayed(runnable, delay)
        }
    }

    fun cancelRunOnUIThread(runnable: Runnable) {
        Moviemade.AppHandler.removeCallbacks(runnable)
    }
}