package org.michaelbel.moviemade.utils

import org.michaelbel.moviemade.Moviemade

object AndroidUtil {

    /*public static String formatCurrency(int currencyCount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        return getContext().getString(R.string.CurrencyCount, numberFormat.format(currencyCount));
    }*/

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