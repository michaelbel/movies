package org.michaelbel.moviemade.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.rest.model.Company;
import org.michaelbel.moviemade.rest.model.Country;
import org.michaelbel.moviemade.rest.model.Genre;

import java.io.File;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

public class AndroidUtils {

    private static final int FLAG_TAG_BR = 1;
    private static final int FLAG_TAG_BOLD = 2;
    private static final int FLAG_TAG_COLOR = 4;
    private static final int FLAG_TAG_ALL = FLAG_TAG_BR | FLAG_TAG_BOLD;

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static String loadProperty(String key) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = getContext().getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception e) {
            // todo Error retrieving file asset
        }

        return null;
    }

    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }

        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            // todo Error
        }
    }

    public static boolean isKeyboardShowed(View view) {
        if (view == null) {
            return false;
        }

        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputManager.isActive(view);
        } catch (Exception e) {
            // todo Error
        }

        return false;
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }

        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            // todo Error
        }
    }

    public static boolean includeAdult() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("adult", true);
    }

    public static boolean scrollToTop() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("scroll_to_top", true);
    }

    public static int viewType() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        return prefs.getInt("view_type", 0);
    }

    public static String posterSize() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        return prefs.getString("image_quality_poster", "w342");
    }

    public static String backdropSize() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        return prefs.getString("image_quality_backdrop", "w780");
    }

    public static String profileSize() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        return prefs.getString("image_quality_profile", "w185");
    }

    public static int getSpanForMovies() {
        if (viewType() == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public static void copyToClipboard(@NonNull CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(text, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipData);
        }
    }

    public static boolean isPermissionGranted(@NonNull String permissionName) {
        int permission = ActivityCompat.checkSelfPermission(getContext(), permissionName);
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(@NonNull String permission, Activity activity, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
    }

    public static SpannableStringBuilder replaceTags(String str) {
        return replaceTags(str, FLAG_TAG_ALL);
    }

    public static SpannableStringBuilder replaceTags(String str, int flag) {
        try {
            int start;
            int end;
            StringBuilder stringBuilder = new StringBuilder(str);
            if ((flag & FLAG_TAG_BR) != 0) {
                while ((start = stringBuilder.indexOf("<br>")) != -1) {
                    stringBuilder.replace(start, start + 4, "\n");
                }
                while ((start = stringBuilder.indexOf("<br/>")) != -1) {
                    stringBuilder.replace(start, start + 5, "\n");
                }
            }
            ArrayList<Integer> bolds = new ArrayList<>();
            if ((flag & FLAG_TAG_BOLD) != 0) {
                while ((start = stringBuilder.indexOf("<b>")) != -1) {
                    stringBuilder.replace(start, start + 3, "");
                    end = stringBuilder.indexOf("</b>");
                    if (end == -1) {
                        end = stringBuilder.indexOf("<b>");
                    }
                    stringBuilder.replace(end, end + 4, "");
                    bolds.add(start);
                    bolds.add(end);
                }
            }
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuilder);
            for (int a = 0; a < bolds.size() / 2; a++) {
                spannableStringBuilder.setSpan(new TypefaceSpan("sans-serif-medium"), bolds.get(a * 2), bolds.get(a * 2 + 1), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return spannableStringBuilder;
        } catch (Exception e) {
            // todo Error
        }

        return new SpannableStringBuilder(str);
    }

    public static String formatOriginalLanguage(String languageCode) {
        String formattedCode = null;

        if (Objects.equals(languageCode, "en")) {
            formattedCode = "English";
        }

        return formattedCode;
    }

    public static String formatCurrency(int currencyCount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        return getContext().getString(R.string.CurrencyCount, numberFormat.format(currencyCount));
    }

    public static String formatRuntime(int runtime) {
        String patternMin = "mm";
        String patternHours = "H:mm";

        SimpleDateFormat formatMin = new SimpleDateFormat(patternMin, Locale.US);
        SimpleDateFormat formatHours = new SimpleDateFormat(patternHours, Locale.US);

        Date date = null;

        try {
            date = formatMin.parse(String.valueOf(runtime));
        } catch (ParseException e) {
            // todo Error.
        }

        return getContext().getString(R.string.MovieRuntime, runtime, formatHours.format(date));
    }

    @Deprecated
    public static String formatCompanies(List<Company> companies) {
        if (companies == null) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        for (Company company : companies) {
            text.append(company.name);
            if (company != companies.get(companies.size() - 1)) {
                text.append(", ");
            }
        }

        return text.toString();
    }

    @Deprecated
    public static String formatCountries(List<Country> countries) {
        if (countries == null) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        for (Country country : countries) {
            if (country.name.equals("United States of America")) {
                country.name = "USA";
            } else if (country.name.equals("United Kingdom")) {
                country.name = "UK";
            } else if (country.name.equals("United Arab Emirates")) {
                country.name = "UAE";
            }

            text.append(country.name);
            if (country != countries.get(countries.size() - 1)) {
                text.append(", ");
            }
        }

        return text.toString();
    }

    @Deprecated
    public static String formatGenres(List<Genre> genres) {
        if (genres == null) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        for (Genre genre : genres) {
            text.append(genre.name);
            if (genre != genres.get(genres.size() - 1)) {
                text.append(", ");
            }
        }

        return text.toString();
    }

    public static String formatNames(List<String> names) {
        if (names == null) {
            return "";
        }

        StringBuilder text = new StringBuilder();
        for (String name : names) {
            text.append(name);
            if (!Objects.equals(name, names.get(names.size() - 1))) {
                text.append(", ");
            }
        }

        return text.toString();
    }

//--------------------------------------------------------------------------------------------------

    public static void createCacheDirectory() {
        File cacheDir = new File(Environment.getExternalStorageDirectory(), "Moviemade");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File cacheDirImages = new File(Environment.getExternalStorageDirectory() + "/Moviemade", "Moviemade Images");
        if (!cacheDirImages.exists()) {
            cacheDirImages.mkdirs();
        }
    }

    public static String getCacheDirectory() {
        File directory = new File(Environment.getExternalStorageDirectory(), "Moviemade/Moviemade Images");
        if (!directory.exists()) {
            createCacheDirectory();
        }

        return directory.getPath();
    }

    public static int getColumns() {
        SharedPreferences prefs = Moviemade.AppContext.getSharedPreferences("main_config", Context.MODE_PRIVATE);
        int type = prefs.getInt("view_type", 0);

        if (type == 0) {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 6 : 8 : ScreenUtils.isPortrait() ? 3 : 5;
        } else if (type == 1) {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
        } else {
            return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
        }
    }

    public static int getColumnsForVideos() {
        return ScreenUtils.isTablet() ? ScreenUtils.isPortrait() ? 2 : 4 : ScreenUtils.isPortrait() ? 1 : 2;
    }

    private static Point displaySize = new Point();
    private static boolean usingHardwareInput;
    private static DisplayMetrics displayMetrics = new DisplayMetrics();

    public static void runOnUIThread(Runnable runnable, long delay) {
        if (delay == 0) {
            Moviemade.AppHandler.post(runnable);
        } else {
            Moviemade.AppHandler.postDelayed(runnable, delay);
        }
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        Moviemade.AppHandler.removeCallbacks(runnable);
    }

    static {
        checkDisplaySize();
    }

    private static void checkDisplaySize() {
        try {
            Configuration configuration = Moviemade.AppContext.getResources().getConfiguration();
            usingHardwareInput = configuration.keyboard != Configuration.KEYBOARD_NOKEYS && configuration.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO;
            WindowManager manager = (WindowManager) Moviemade.AppContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
        } catch (Exception e) {
            // todo Error.
        }
    }
}