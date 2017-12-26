package org.michaelbel.application.ui.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.ui.SettingsActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.cell.EmptyCell;
import org.michaelbel.application.ui.view.cell.TextCell;
import org.michaelbel.application.ui.view.cell.TextDetailCell;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.ScreenUtils;
import org.michaelbel.bottomsheet.BottomSheet;

public class SettingsFragment extends Fragment {

    private int rowCount;
    private int imageQualityRow;
    private int inAppBrowserRow;
    private int adultRow;
    private int emptyRow;

    private ListAdapter adapter;
    private SharedPreferences prefs;
    private SettingsActivity activity;
    private LinearLayoutManager layoutManager;

    private RecyclerListView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SettingsActivity) getActivity();

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.toolbarTextView.setText(R.string.Settings);

        rowCount = 0;
        imageQualityRow = rowCount++;
        adultRow = rowCount++;
        inAppBrowserRow = rowCount++;
        emptyRow = rowCount++;

        adapter = new ListAdapter();
        layoutManager = new LinearLayoutManager(activity);
        prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (position == imageQualityRow) {
                BottomSheet.Builder builder = new BottomSheet.Builder(activity);
                builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
                builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
                builder.setCellHeight(ScreenUtils.dp(52));
                builder.setItems(new int[] { R.string.ImageQualityLow, R.string.ImageQualityMedium, R.string.ImageQualityHigh, R.string.ImageQualityOriginal}, (dialogInterface, i) -> {
                    String imageQualityBackdrop;
                    String imageQualityLogo;
                    String imageQualityPoster;
                    String imageQualityProfile;
                    String imageQualityStill;

                    String imageQuality;

                    if (i == 0) {
                        imageQuality = getString(R.string.ImageQualityLow);

                        imageQualityBackdrop = "w300";
                        imageQualityLogo = "w45";
                        imageQualityPoster = "w92";
                        imageQualityProfile = "w45";
                        imageQualityStill = "w92";
                    } else if (i == 1) {
                        imageQuality = getString(R.string.ImageQualityMedium);

                        imageQualityBackdrop = "w780";
                        imageQualityLogo = "w185";
                        imageQualityPoster = "w342";
                        imageQualityProfile = "w185";
                        imageQualityStill = "w185";
                    } else if (i == 2) {
                        imageQuality = getString(R.string.ImageQualityHigh);

                        imageQualityBackdrop = "w1280";
                        imageQualityLogo = "w500";
                        imageQualityPoster = "w780";
                        imageQualityProfile = "w632";
                        imageQualityStill = "w300";
                    } else {
                        imageQuality = getString(R.string.ImageQualityOriginal);

                        imageQualityBackdrop = "original";
                        imageQualityLogo = "original";
                        imageQualityPoster = "original";
                        imageQualityProfile = "original";
                        imageQualityStill = "original";
                    }

                    SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality", imageQuality);
                    editor.putString("image_quality_backdrop", imageQualityBackdrop);
                    editor.putString("image_quality_logo", imageQualityLogo);
                    editor.putString("image_quality_poster", imageQualityPoster);
                    editor.putString("image_quality_profile", imageQualityProfile);
                    editor.putString("image_quality_still", imageQualityStill);
                    editor.putBoolean("image_quality_custom", false);
                    editor.apply();

                    if (view instanceof TextDetailCell) {
                        ((TextDetailCell) view).setValue(imageQuality);
                    }
                });
                builder.show();
            } else if (position == inAppBrowserRow) {
                SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("in_app_browser", true);
                editor.putBoolean("in_app_browser", !enable);
                editor.apply();
                if (view instanceof TextDetailCell) {
                    ((TextDetailCell) view).setChecked(!enable);
                }
            } else if (position == adultRow) {
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("adult", true);
                editor.putBoolean("adult", !enable);
                editor.apply();
                if (view instanceof TextDetailCell) {
                    ((TextDetailCell) view).setChecked(!enable);
                }
            }
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = layoutManager.onSaveInstanceState();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.onRestoreInstanceState(state);
    }

    public class ListAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View cell;

            if (type == 1) {
                cell = new EmptyCell(activity);
            } else if (type == 2) {
                cell = new TextDetailCell(activity);
            } else {
                cell = new TextCell(activity);
            }

            return new Holder(cell);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.setMode(EmptyCell.MODE_DEFAULT);

                if (position == emptyRow) {
                    cell.setHeight(ScreenUtils.dp(12));
                }
            } else if (type == 2) {
                TextDetailCell cell = (TextDetailCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == inAppBrowserRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.InAppBrowser);
                    cell.setValue(R.string.InAppBrowserInfo);
                    cell.setChecked(prefs.getBoolean("in_app_browser", true));
                } else if (position == adultRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(getString(R.string.IncludeAdult));
                    cell.setValue("Toggle the inclusion of adult content");
                    cell.setChecked(prefs.getBoolean("adult", true));
                    cell.setDivider(true);
                } else if (position == imageQualityRow) {
                    String imageQuality = prefs.getString("image_quality", getString(R.string.ImageQualityMedium));
                    boolean customSettings = prefs.getBoolean("image_quality_settings_customize", false);

                    cell.setText(getString(R.string.ImageQuality));
                    cell.setValue(customSettings ? getString(R.string.Custom) : imageQuality);
                    cell.setDivider(true);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == emptyRow) {
                return 1;
            } else if (position == inAppBrowserRow || position == imageQualityRow || position == adultRow) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}