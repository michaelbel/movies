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
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.michaelbel.bottomsheetdialog.BottomSheet;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.SettingsActivity;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.ui.view.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.view.cell.TextDetailCellDev;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtilsDev;
import org.michaelbel.moviemade.util.DateUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

public class SettingsAdvancedFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int scrollbarsRow;
    private int zoomReviewRow;
    private int emptyRow2;

    private int floatingToolbarRow;
    private int searchResultsCounterRow;
    private int hamburgerMenuRow;
    private int fullOverviewRow;
    private int emptyRow1;
    private int deleteRatingsRow;

    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerListView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SettingsActivity) getActivity();

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.binding.toolbarTitle.setText(R.string.AdvancedSettings);

        rowCount = 0;
        infoRow = rowCount++;
        scrollbarsRow = rowCount++;
        zoomReviewRow = rowCount++;
        emptyRow2 = rowCount++;

        //floatingToolbarRow = rowCount++;
        //searchResultsCounterRow = rowCount++;
        //hamburgerMenuRow = rowCount++;
        //fullOverviewRow = rowCount++;
        //emptyRow1 = rowCount++;
        //deleteRatingsRow = rowCount++;

        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new SettingsAdvancedAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            if (position == scrollbarsRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scrollbars", true);
                editor.putBoolean("scrollbars", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == zoomReviewRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("zoom_review", true);
                editor.putBoolean("zoom_review", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == floatingToolbarRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scroll_appbar", false);
                editor.putBoolean("scroll_appbar", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == searchResultsCounterRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("search_results_count", false);
                editor.putBoolean("search_results_count", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == hamburgerMenuRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("burger", false);
                editor.putBoolean("burger", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == fullOverviewRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("full_overview", false);
                editor.putBoolean("full_overview", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == deleteRatingsRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("scrollbars_rating", 0);
                editor.putInt("floating_toolbar_rating", 0);
                editor.putInt("scroll_to_top_rating", 0);
                editor.putInt("search_results_count_rating", 0);
                editor.putInt("hamburger_menu_rating", 0);
                editor.putInt("full_overview_rating", 0);
                editor.apply();

                recyclerView.invalidateViews();
            }
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            if (position == scrollbarsRow || position == zoomReviewRow || position == floatingToolbarRow || position == searchResultsCounterRow || position == hamburgerMenuRow || position == fullOverviewRow) {
                rateFeature(view, position);
                return true;
            }

            return false;
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    private void rateFeature(View view, int position) {
        String featureName = ((TextDetailCellDev) view).getTitleText();

        BottomSheet.Builder builder = new BottomSheet.Builder(activity, false);
        builder.setTitle(featureName);
        builder.setTitleMultiline(true);
        builder.setTitleTextColor(ContextCompat.getColor(activity, Theme.accentColor()));
        builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        builder.setItems(new CharSequence[] { "5 stars", "4 stars", "3 stars", "2 stars", "1 star" }, (dialog, which) -> {
            SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            int rating = 0;

            if (which == 0) rating = 5;
            if (which == 1) rating = 4;
            if (which == 2) rating = 3;
            if (which == 3) rating = 2;
            if (which == 4) rating = 1;

            if (position == scrollbarsRow) {
                editor.putInt("scrollbars_rating", rating);
                sendAnalytics("Scrollbars", rating);
            } else if (position == zoomReviewRow) {
                editor.putInt("zoom_review_rating", rating);
                sendAnalytics("Zoom Review", rating);
            }

            if (position == floatingToolbarRow) {
                editor.putInt("floating_toolbar_rating", rating);
            } else if (position == searchResultsCounterRow) {
                editor.putInt("search_results_count_rating", rating);
            } else if (position == hamburgerMenuRow) {
                editor.putInt("hamburger_menu_rating", rating);
            } else if (position == fullOverviewRow) {
                editor.putInt("full_overview_rating", rating);
            }

            editor.apply();

            ((TextDetailCellDev) view).setRating(rating);
        });
        builder.show();
    }

    private void sendAnalytics(String option, int stars) {
        Tracker tracker = ((Moviemade) getActivity().getApplication()).getDefaultTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("Advanced Settings")
                .setAction("Rated option: " + option + " with " + stars + " stars. Time: " + DateUtils.getCurrentDateAndTime())
                .build());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    public class SettingsAdvancedAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View cell;

            if (type == 1) {
                cell = new EmptyCell(activity);
            } else if (type == 2) {
                cell = new TextCell(activity);
            } else {
                cell = new TextDetailCellDev(activity);
            }

            return new Holder(cell);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == infoRow) {
                    cell.setMode(EmptyCell.MODE_TEXT);
                    cell.setText("Advanced settings are experimental, they can work unstable or after restarting this app.");
                } else if (position == emptyRow1 || position == emptyRow2) {
                    cell.setMode(EmptyCell.MODE_DEFAULT);
                    cell.setHeight(ScreenUtils.dp(12));
                }
            } else if (type == 2) {
              TextCell cell = (TextCell) holder.itemView;
              cell.changeLayoutParams();

              if (position == deleteRatingsRow) {
                  cell.setText("Delete all ratings");
                  cell.setHeight(ScreenUtils.dp(52));
                  cell.getTextView().setTextColor(0xFFFF5252);
              }
            } else if (type == 3) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);

                TextDetailCellDev cell = (TextDetailCellDev) holder.itemView;
                cell.setMode(TextDetailCell.MODE_SWITCH);
                cell.changeLayoutParams();

                if (position == scrollbarsRow) {
                    cell.setText(R.string.Scrollbars);
                    cell.setValue(R.string.ScrollbarsInfo);
                    cell.setChecked(AndroidUtilsDev.scrollbars());
                    cell.setRating(prefs.getInt("scrollbars_rating", 0));
                    cell.setDivider(true);
                } else if (position == zoomReviewRow) {
                    cell.setText("Zoom Review");
                    cell.setValue("Enable review gestures control");
                    cell.setChecked(AndroidUtilsDev.zoomReview());
                    cell.setRating(prefs.getInt("zoom_review_rating", 0));
                }

                if (position == floatingToolbarRow) {
                    cell.setText(R.string.FloatingToolbar);
                    cell.setValue("Enable to scrollable toolbar");
                    cell.setChecked(AndroidUtilsDev.floatingToolbar());
                    cell.setRating(prefs.getInt("floating_toolbar_rating", 0));
                    cell.setDivider(true);
                } else if (position == searchResultsCounterRow) {
                    cell.setText(R.string.SearchResultsCounter);
                    cell.setValue("Show count of results in search tab");
                    cell.setChecked(AndroidUtilsDev.searchResultsCount());
                    cell.setRating(prefs.getInt("search_results_count_rating", 0));
                    cell.setDivider(true);
                } else if (position == hamburgerMenuRow) {
                    cell.setText(R.string.HamburgerMenu);
                    cell.setValue("Set hamburger menu icon");
                    cell.setChecked(AndroidUtilsDev.hamburgerIcon());
                    cell.setRating(prefs.getInt("hamburger_menu_rating", 0));
                    cell.setDivider(true);
                } else if (position == fullOverviewRow) {
                    cell.setText(R.string.FullOverview);
                    cell.setValue("Display overview fully");
                    cell.setChecked(AndroidUtilsDev.hamburgerIcon());
                    cell.setRating(prefs.getInt("full_overview_rating", 0));
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == infoRow || position == emptyRow1 || position == emptyRow2) {
                return 1;
            } else if (position == deleteRatingsRow) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}