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

@SuppressWarnings("all")
public class SettingsAdvancedFragment extends Fragment {

    private int rowCount;
    private int scrollbarsRow;
    private int emptyRow;

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
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTextView.setText("Advanced Settings");

        rowCount = 0;
        scrollbarsRow = rowCount++;
        emptyRow = rowCount++;

        layoutManager = new LinearLayoutManager(activity);
        prefs = activity.getSharedPreferences("devconfig", Activity.MODE_PRIVATE);

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

                if (position == scrollbarsRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText("Enable Scrollbars");
                    cell.setValue("Enable all scrollbars in the app");
                    cell.setChecked(prefs.getBoolean("scrollbars", true));
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
            } else if (position == scrollbarsRow) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}