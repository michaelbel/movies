package org.michaelbel.application.ui.fragment;

import android.app.Activity;
import android.content.DialogInterface;
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
import org.michaelbel.application.ui.view.cell.TextDetailCellDev;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtilsDev;
import org.michaelbel.application.util.ScreenUtils;
import org.michaelbel.bottomsheet.BottomSheet;

@SuppressWarnings("all")
public class SettingsAdvancedFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int scrollbarsRow;
    private int floatingToolbarRow;
    private int scrollToTopRow;
    private int searchResultsCounterRow;
    private int hamburgerMenuRow;
    private int fullOverviewRow;
    private int emptyRow1;
    private int deleteRatingsRow;
    private int emptyRow2;

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
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTextView.setText(R.string.AdvancedSettings);

        rowCount = 0;
        infoRow = rowCount++;
        scrollbarsRow = rowCount++;
        floatingToolbarRow = rowCount++;
        scrollToTopRow = rowCount++;
        searchResultsCounterRow = rowCount++;
        hamburgerMenuRow = rowCount++;
        fullOverviewRow = rowCount++;
        emptyRow1 = rowCount++;
        deleteRatingsRow = rowCount++;
        emptyRow2 = rowCount++;

        layoutManager = new LinearLayoutManager(activity);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new ListAdapter());
        recyclerView.setLayoutManager(layoutManager);
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
            } else if (position == floatingToolbarRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scroll_appbar", false);
                editor.putBoolean("scroll_appbar", !enable);
                editor.apply();
                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setChecked(!enable);
                }
            } else if (position == scrollToTopRow) {
                SharedPreferences prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                boolean enable = prefs.getBoolean("scroll_to_top", false);
                editor.putBoolean("scroll_to_top", !enable);
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

            }
        });
        recyclerView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(View view, int position) {
                if (position == scrollbarsRow || position == floatingToolbarRow || position == scrollToTopRow || position == searchResultsCounterRow || position == hamburgerMenuRow || position == fullOverviewRow) {
                    rateFeachure(view, position);
                    return true;
                }

                return false;
            }
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    private void rateFeachure(View view, int position) {
        String feachureName = ((TextDetailCellDev) view).getTitleText();

        BottomSheet.Builder builder = new BottomSheet.Builder(activity, false);
        builder.setTitle(feachureName);
        builder.setTitleMultiline(true);
        builder.setTitleTextColor(ContextCompat.getColor(activity, Theme.accentColor()));
        builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        builder.setItems(new CharSequence[] { "5 stars", "4 stars", "3 stars", "2 stars", "1 star" }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                } else if (position == floatingToolbarRow) {
                    editor.putInt("floating_toolbar_rating", rating);
                } else if (position == scrollToTopRow) {
                    editor.putInt("hamburger_menu_rating", rating);
                } else if (position == searchResultsCounterRow) {
                    editor.putInt("scroll_to_top_rating", rating);
                } else if (position == hamburgerMenuRow) {
                    editor.putInt("search_results_count_rating", rating);
                } else if (position == fullOverviewRow) {
                    editor.putInt("full_overview_rating", rating);
                }

                editor.apply();

                if (view instanceof TextDetailCellDev) {
                    ((TextDetailCellDev) view).setRating(rating);
                }
            }
        });
        builder.show();
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

                if (position == infoRow) {
                    cell.setMode(EmptyCell.MODE_TEXT);
                    cell.setText("Раз уж Вам удалось проникнуть в раздел с этими настройками, то смело используйте их для лучшей кастомизации приложения. Некоторые настройки могут работать не стабильно или активироваться после перезапуска приложения. Если долго нажимать на фичу, можно поставить ей рейтинг. Это полностью анонимно.");
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
                    cell.setValue("Enable all scrollbars in the app");
                    cell.setChecked(AndroidUtilsDev.scrollbarsEnabled());
                    cell.setRating(prefs.getInt("scrollbars_rating", 0));
                    cell.setDivider(true);
                } else if (position == floatingToolbarRow) {
                    cell.setText(R.string.FloatingToolbar);
                    cell.setValue("Enable to scrollable toolbar");
                    cell.setChecked(AndroidUtilsDev.floatingToolbar());
                    cell.setRating(prefs.getInt("floating_toolbar_rating", 0));
                    cell.setDivider(true);
                } else if (position == scrollToTopRow) {
                    cell.setText(R.string.ScrollToTop);
                    cell.setValue("Scroll list to top when tab click");
                    cell.setChecked(AndroidUtilsDev.scrollToTop());
                    cell.setRating(prefs.getInt("scroll_to_top_rating", 0));
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