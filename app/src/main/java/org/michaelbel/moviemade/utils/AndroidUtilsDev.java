package org.michaelbel.moviemade.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import org.michaelbel.moviemade.Moviemade;

public class AndroidUtilsDev {

    private static Context getContext() {
        return Moviemade.AppContext;
    }

    public static boolean floatingToolbar() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("floating_toolbar", false);
    }

    public static boolean searchResultsCount() {
        SharedPreferences prefs = getContext().getSharedPreferences("devconfig", Context.MODE_PRIVATE);
        return prefs.getBoolean("search_results_count", false);
    }

    public static AppBarLayout.LayoutParams getLayoutParams(View view) {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) view.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);
        return params;
    }
}