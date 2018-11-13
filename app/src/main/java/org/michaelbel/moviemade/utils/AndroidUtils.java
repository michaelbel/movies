package org.michaelbel.moviemade.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Point;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Country;
import org.michaelbel.moviemade.rest.model.v3.Genre;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidUtils {

    public static final int VIEW_POSTERS = 0;
    public static final int VIEW_BACKDROPS = 1;

    public static int viewType() {
        return VIEW_POSTERS;
    }







    private static Context getContext() {
        return Moviemade.AppContext;
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
            e.printStackTrace();
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

    public static boolean scrollbars() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("scrollbars", true);
    }

    public static boolean zoomReview() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("zoom_review", true);
    }

    public static boolean fullOverview() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("full_overview", false);
    }

    public static boolean textSelect() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("text_select", false);
    }

    public static String posterSize() {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
        return prefs.getString("image_quality_poster", "w342");
    }

    private static final int FLAG_TAG_BR = 1;
    private static final int FLAG_TAG_BOLD = 2;
    private static final int FLAG_TAG_ALL = FLAG_TAG_BR | FLAG_TAG_BOLD;

    public static SpannableStringBuilder replaceTags(String str) {
        return replaceTags(str, FLAG_TAG_ALL);
    }

    private static SpannableStringBuilder replaceTags(String str, int flag) {
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
            e.printStackTrace();
        }

        return new SpannableStringBuilder(str);
    }

    public static String formatOriginalLanguage(String languageCode) {
        String formattedCode;

        if (Objects.equals(languageCode, "en")) {
            formattedCode = "English";
        } else {
            formattedCode = languageCode;
        }

        return formattedCode;
    }

    public static String formatCurrency(int currencyCount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        return getContext().getString(R.string.CurrencyCount, numberFormat.format(currencyCount));
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

    @Deprecated
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

    public static Point displaySize = new Point();
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
            WindowManager manager = (WindowManager) Moviemade.AppContext.getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getMetrics(displayMetrics);
                    display.getSize(displaySize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isTablet() {
        return false;
    }
}