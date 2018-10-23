package org.michaelbel.moviemade.app.extensions;

import android.content.SharedPreferences;
import android.text.TextUtils;

import org.michaelbel.material.annotation.Beta;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidExtensions extends Extensions {

    /*public static AppBarLayout.LayoutParams setScrollFlags(View view) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) view.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        return params;
    }*/

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

        return Moviemade.AppContext.getString(R.string.MovieRuntime, formatHour.format(date), runtime);
    }

    public static String formatReleaseDate(String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty()) {
            return "";
        }

        String pattern = "yyyy-MM-dd";
        String newPattern = "d MMM yyyy";

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(newPattern, Locale.US);

        Date date = null;
        try {
            date = format.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormat.format(date);
    }

    @Beta
    public static String formatBirthday(String birthDate) {
        if (TextUtils.isEmpty(birthDate)) {
            return null;
        }

        String pattern = "yyyy-MM-dd";
        String newPattern = "MMM d, yyyy";

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat(newPattern, Locale.US);

        Date date = null;
        try {
            date = format.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newFormat.format(date);
    }

    @Beta
    public static int getAge(String dateOfBirth) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(dateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age;

        birthDate.setTime(date);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    @Beta
    public static int getAgeDeath(String dateOfBirth, String dateOfDeath) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date_birth = null;
        Date date_death = null;
        try {
            date_birth = format.parse(dateOfBirth);
            date_death = format.parse(dateOfDeath);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar birthDate = Calendar.getInstance();
        Calendar deathDate = Calendar.getInstance();

        int age;

        birthDate.setTime(date_birth);
        deathDate.setTime(date_death);
        //if (birthDate.after(deathDate)) {
        //    throw new IllegalArgumentException("Can't be born in the future");
        //}

        age = deathDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (deathDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}