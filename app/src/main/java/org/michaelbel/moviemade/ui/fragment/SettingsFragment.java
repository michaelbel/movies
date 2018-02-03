package org.michaelbel.moviemade.ui.fragment;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.bottomsheetdialog.BottomSheet;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.eventbus.Events;
import org.michaelbel.moviemade.ui.SettingsActivity;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.ui.view.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

public class SettingsFragment extends Fragment {

    private int asc = 0;

    private int rowCount;
    //private int storageUsageRow;
    //private int searchHistoryRow;
    //private int emptyRow1;
    private int viewTypeRow;
    //private int imageQualityRow;
    private int adultRow;
    private int inAppBrowserRow;
    private int scrollToTopRow;
    private int zoomReviewRow;
    private int fullOverviewRow;
    private int scrollbarsRow;
    private int emptyRow2;

    private String[] viewType = new String[] { "List Big", "Posters" };
    //private int viewType2 = R.array.MovieViewType;

    private SharedPreferences prefs;
    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (SettingsActivity) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        menu.add(null)
            .setIcon(Theme.getIcon(R.drawable.ic_settings, ContextCompat.getColor(activity, Theme.primaryColor())))
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                asc++;
                if (asc == 5) {
                    //activity.startFragment(new SettingsAdvancedFragment(), activity.binding.fragmentView, "settingsAdvancedFragment");
                    asc = 0;
                }
                return true;
            });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.binding.toolbarTitle.setText(R.string.Settings);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        rowCount = 0;
        //storageUsageRow = rowCount++;
        //searchHistoryRow = rowCount++;
        //emptyRow1 = rowCount++;
        viewTypeRow = rowCount++;
        //imageQualityRow = rowCount++;
        adultRow = rowCount++;
        inAppBrowserRow = rowCount++;
        scrollToTopRow = rowCount++;
        zoomReviewRow = rowCount++;
        fullOverviewRow = rowCount++;
        scrollbarsRow = rowCount++;
        emptyRow2 = rowCount++;

        prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new SettingsAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            /*if (position == storageUsageRow) {
                activity.startFragment(new StorageFragment(), activity.binding.fragmentView, "storageFragment");
            } else if (position == searchHistoryRow) {
                activity.startFragment(new SearchHistoryFragment(), activity.binding.fragmentView, "searchHistoryFragment");
            } else if (position == imageQualityRow) {
                BottomSheet.Builder builder = new BottomSheet.Builder(activity);
                builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
                builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
                builder.setCellHeight(ScreenUtils.dp(52));
                builder.setItems(new int[] { R.string.ImageQualityLow, R.string.ImageQualityMedium, R.string.ImageQualityHigh, R.string.ImageQualityOriginal }, (dialogInterface, i) -> {
                    String backdropSize;
                    String logoSize;
                    String posterSize;
                    String profileSize;
                    String stillSize;

                    String imageSize;

                    if (i == 0) {
                        imageSize = getString(R.string.ImageQualityLow);

                        backdropSize = "w300";
                        logoSize = "w45";
                        posterSize = "w92";
                        profileSize = "w45";
                        stillSize = "w92";
                    } else if (i == 1) {
                        imageSize = getString(R.string.ImageQualityMedium);

                        backdropSize = "w780";
                        logoSize = "w185";
                        posterSize = "w342";
                        profileSize = "w185";
                        stillSize = "w185";
                    } else if (i == 2) {
                        imageSize = getString(R.string.ImageQualityHigh);

                        backdropSize = "w1280";
                        logoSize = "w500";
                        posterSize = "w780"; *//* w632 ? *//*
                        profileSize = "w185";
                        stillSize = "w300";
                    } else {
                        imageSize = getString(R.string.ImageQualityOriginal);

                        backdropSize = "original";
                        logoSize = "original";
                        posterSize = "original";
                        profileSize = "original";
                        stillSize = "original";
                    }

                    SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality", imageSize);
                    editor.putString("image_quality_backdrop", backdropSize);
                    editor.putString("image_quality_logo", logoSize);
                    editor.putString("image_quality_poster", posterSize);
                    editor.putString("image_quality_profile", profileSize);
                    editor.putString("image_quality_still", stillSize);
                    editor.putBoolean("image_quality_custom", false);
                    editor.apply();

                    if (view instanceof TextDetailCell) {
                        ((TextDetailCell) view).setValue(imageSize);
                    }

                    ((Moviemade) activity.getApplication()).eventBus().send(new Events.MovieListUpdateImageQuality());
                });
                builder.show();
            } else*/
            if (position == viewTypeRow) {
                BottomSheet.Builder builder = new BottomSheet.Builder(activity);
                builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
                builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
                builder.setCellHeight(ScreenUtils.dp(52));
                builder.setItems(viewType, (dialog, which) -> {
                    SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("view_type", which);
                    editor.apply();

                    if (view instanceof TextDetailCell) {
                        ((TextDetailCell) view).setValue(viewType[which]);
                    }

                    ((Moviemade) activity.getApplication()).eventBus().send(new Events.MovieListRefreshLayout());
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
                ((Moviemade) activity.getApplication()).eventBus().send(new Events.MovieListUpdateAdult());
            } else if (position == scrollToTopRow) {
                SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scroll_to_top", true);
                editor.putBoolean("scroll_to_top", !enable);
                editor.apply();
                if (view instanceof TextDetailCell) {
                    ((TextDetailCell) view).setChecked(!enable);
                }
            } else if (position == zoomReviewRow) {
                SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("zoom_review", true);
                editor.putBoolean("zoom_review", !enable);
                editor.apply();
                if (view instanceof TextDetailCell) {
                    ((TextDetailCell) view).setChecked(!enable);
                }
            } else if (position == scrollbarsRow) {
                SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scrollbars", true);
                editor.putBoolean("scrollbars", !enable);
                editor.apply();
                if (view instanceof TextDetailCell) {
                    ((TextDetailCell) view).setChecked(!enable);
                }
            } else if (position == fullOverviewRow) {
                SharedPreferences prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("full_overview", false);
                editor.putBoolean("full_overview", !enable);
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
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    public class SettingsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View cell = null;

            if (type == 1) {
                cell = new EmptyCell(activity);
            } else if (type == 2) {
                cell = new TextCell(activity);
            } else if (type == 3) {
                cell = new TextDetailCell(activity);
            }

            return new Holder(cell);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.changeLayoutParams()
                    .setMode(EmptyCell.MODE_DEFAULT)
                    .setHeight(ScreenUtils.dp(12));
            } else if (type == 2) {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams()
                    .setHeight(ScreenUtils.dp(52));

                /*if (position == storageUsageRow) {
                    cell.setText(R.string.StorageUsage);
                    cell.setDivider(true);
                } else if (position == searchHistoryRow) {
                    cell.setText(R.string.SearchHistory);
                }*/
            } else if (type == 3) {
                TextDetailCell cell = (TextDetailCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == inAppBrowserRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.InAppBrowser);
                    cell.setValue(R.string.InAppBrowserInfo);
                    cell.setChecked(prefs.getBoolean("in_app_browser", true));
                    cell.setDivider(true);
                } else if (position == adultRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(getString(R.string.IncludeAdult));
                    cell.setValue(R.string.IncludeAdultInfo);
                    cell.setChecked(AndroidUtils.includeAdult());
                    cell.setDivider(true);
                } /*else if (position == imageQualityRow) {
                    String imageQuality = prefs.getString("image_quality", getString(R.string.ImageQualityMedium));
                    boolean customSettings = prefs.getBoolean("image_quality_settings_customize", false);

                    cell.setText(getString(R.string.ImageQuality));
                    cell.setValue(customSettings ? "Custom" : imageQuality);
                    cell.setDivider(true);
                }*/ else if (position == viewTypeRow) {
                    cell.setMode(TextDetailCell.MODE_DEFAULT);
                    cell.setText(R.string.ViewType);
                    cell.setValue(viewType[AndroidUtils.viewType()]);
                    cell.setDivider(true);
                } else if (position == scrollToTopRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.ScrollToTop);
                    cell.setValue(R.string.ScrollToTopInfo);
                    cell.setChecked(AndroidUtils.scrollToTop());
                    cell.setDivider(true);
                }  else if (position == zoomReviewRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.ZoomReview);
                    cell.setValue(R.string.ZoomReviewInfo);
                    cell.setChecked(AndroidUtils.zoomReview());
                    cell.setDivider(true);
                } else if (position == fullOverviewRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.FullOverview);
                    cell.setValue(R.string.FullOverviewInfo);
                    cell.setChecked(AndroidUtils.fullOverview());
                    cell.setDivider(true);
                } else if (position == scrollbarsRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.Scrollbars);
                    cell.setValue(R.string.ScrollbarsInfo);
                    cell.setChecked(AndroidUtils.scrollbars());
                    cell.setDivider(false);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (/*position == emptyRow1 || */position == emptyRow2) {
                return 1;
            } else if (/*position == storageUsageRow || position == searchHistoryRow*/ position == -100) {
                return 2;
            } else if (position == viewTypeRow || /*position == imageQualityRow || */position == adultRow || position == inAppBrowserRow || position == scrollToTopRow || position == zoomReviewRow || position == fullOverviewRow || position == scrollbarsRow) {
                return 3;
            } else {
                return -1;
            }
        }
    }
}