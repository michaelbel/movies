package org.michaelbel.moviemade.app.extensions;

import org.michaelbel.core.extensions.Extensions;
import org.michaelbel.moviemade.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date: Sun, Feb 25 2018
 * Time: 11:28 MSK
 *
 * @author Michael Bel
 */

public class AndroidExtensions extends Extensions {

    public static String formatRuntime(int runtime) {
        String patternMin = "mm";
        String patternHour = "H:mm";

        SimpleDateFormat formatMin = new SimpleDateFormat(patternMin, Locale.US);
        SimpleDateFormat formatHour = new SimpleDateFormat(patternHour, Locale.US);

        Date date = null;

        try {
            date = formatMin.parse(String.valueOf(runtime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return getContext().getString(R.string.MovieRuntime, formatHour.format(date), runtime);
    }
}