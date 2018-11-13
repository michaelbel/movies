package org.michaelbel.moviemade.extensions;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static void clearCursorDrawable(EditText editText) {
        if (editText == null) {
            return;
        }

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.setInt(editText, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Drawable getIcon(Context context, @DrawableRes int resource, int colorFilter) {
        return getIcon(context, resource, colorFilter, PorterDuff.Mode.MULTIPLY);
    }

    public static Drawable getIcon(Context context, @DrawableRes int resource, int colorFilter, PorterDuff.Mode mode) {
        Drawable iconDrawable = ContextCompat.getDrawable(context, resource);

        if (iconDrawable != null) {
            iconDrawable.clearColorFilter();
            iconDrawable.mutate().setColorFilter(colorFilter, mode);
        }

        return iconDrawable;
    }
}