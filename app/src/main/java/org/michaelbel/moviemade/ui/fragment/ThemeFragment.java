package org.michaelbel.moviemade.ui.fragment;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.ui.SettingsActivity;
import org.michaelbel.moviemade.ui.view.cell.ThemeCell;

/**
 * Date: Sun, Mar 4 2018
 * Time: 14:26 MSK
 *
 * @author Michael Bel
 */

public class ThemeFragment extends Fragment implements View.OnClickListener {

    private String[] themes;
    private SettingsActivity activity;

    private ThemeCell lightCell;
    private ThemeCell nightCell;
    private ThemeCell nightBlueCell;
    private LinearLayout fragmentView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SettingsActivity) getActivity();
        themes = getResources().getStringArray(R.array.Themes);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTitle.setText(R.string.Theme);

        fragmentView = new LinearLayout(activity);
        fragmentView.setOrientation(LinearLayout.VERTICAL);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        lightCell = new ThemeCell(activity, Theme.THEME_LIGHT);
        lightCell.setDivider(true);
        lightCell.setText(themes[0]);
        lightCell.setOnClickListener(this);
        fragmentView.addView(lightCell);

        nightCell = new ThemeCell(activity, Theme.THEME_NIGHT);
        nightCell.setDivider(true);
        nightCell.setText(themes[1]);
        nightCell.setOnClickListener(this);
        //fragmentView.addView(nightCell);

        nightBlueCell = new ThemeCell(activity, Theme.THEME_NIGHT_BLUE);
        nightBlueCell.setDivider(false);
        nightBlueCell.setText(themes[2]);
        nightBlueCell.setOnClickListener(this);
        fragmentView.addView(nightBlueCell);

        changeIcon();

        return fragmentView;
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            lightCell.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 56, 0, 56, 0));
            nightBlueCell.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 56, 0, 56, 0));
        } else {
            lightCell.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
            nightBlueCell.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (view == lightCell) {
            if (Theme.getTheme() != Theme.THEME_LIGHT) {
                editor.putInt("theme", Theme.THEME_LIGHT);
                editor.apply();

                changeIcon();
                changeTheme();
            }
        } else if (view == nightCell) {
            if (Theme.getTheme() != Theme.THEME_NIGHT) {
                editor.putInt("theme", Theme.THEME_NIGHT);
                editor.apply();

                changeIcon();
                changeTheme();
            }
        } else if (view == nightBlueCell) {
            if (Theme.getTheme() != Theme.THEME_NIGHT_BLUE) {
                editor.putInt("theme", Theme.THEME_NIGHT_BLUE);
                editor.apply();

                changeIcon();
                changeTheme();
            }
        }
    }

    private void changeIcon() {
        lightCell.setIconChecked(Theme.getTheme() == Theme.THEME_LIGHT);
        nightCell.setIconChecked(Theme.getTheme() == Theme.THEME_NIGHT);
        nightBlueCell.setIconChecked(Theme.getTheme() == Theme.THEME_NIGHT_BLUE);
    }

    private void changeTheme() {
        // Change Window status bar color.
        // activity.toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.night_foregroundColor));

        lightCell.changeTheme();
        nightCell.changeTheme();
        nightBlueCell.changeTheme();

        changeFragmentViewBackground();

        ((Moviemade) activity.getApplication()).eventBus().send(new Events.ChangeTheme());
    }

    private void changeFragmentViewBackground() {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        ObjectAnimator fragmentBackgroundAnimator = ObjectAnimator.ofObject(fragmentView, "backgroundColor", evaluator, 0, 0);

        int startColor = 0, endColor = 0;

        if (Theme.getTheme() == Theme.THEME_LIGHT) {
            startColor = ContextCompat.getColor(activity, R.color.night_blue_backgroundColor);
            endColor = ContextCompat.getColor(activity, R.color.backgroundColor);
        } else if (Theme.getTheme() == Theme.THEME_NIGHT_BLUE) {
            startColor = ContextCompat.getColor(activity, R.color.backgroundColor);
            endColor = ContextCompat.getColor(activity, R.color.night_blue_backgroundColor);
        }

        fragmentBackgroundAnimator.setObjectValues(startColor, endColor);
        fragmentBackgroundAnimator.setDuration(300);
        fragmentBackgroundAnimator.start();
    }
}