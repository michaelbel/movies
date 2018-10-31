package org.michaelbel.moviemade.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.material.widget.Holder;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.eventbus.Events;
import org.michaelbel.moviemade.ui.activity.AboutActivity;
import org.michaelbel.moviemade.ui.activity.SettingsActivity;
import org.michaelbel.moviemade.ui_old.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui_old.view.cell.TextCell;
import org.michaelbel.moviemade.ui_old.view.cell.TextDetailCell;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SettingsFragment extends Fragment {

    private int rowCount;
    private int inAppBrowserRow;
    private int adultRow;
    private int aboutRow;

    private SharedPreferences prefs;
    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SettingsActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(v -> activity.finish());
        activity.toolbarTitle.setText(R.string.Settings);

        rowCount = 0;
        inAppBrowserRow = rowCount++;
        adultRow = rowCount++;
        aboutRow = rowCount++;

        prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new SettingsAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> {
            if (position == inAppBrowserRow) {
                boolean enable = prefs.getBoolean("in_app_browser", true);
                prefs.edit().putBoolean("in_app_browser", !enable).apply();
                if (v instanceof TextDetailCell) {
                    ((TextDetailCell) v).setChecked(!enable);
                }
            } else if (position == adultRow) {
                boolean enable = prefs.getBoolean("adult", true);
                prefs.edit().putBoolean("adult", !enable).apply();
                if (v instanceof TextDetailCell) {
                    ((TextDetailCell) v).setChecked(!enable);
                }
                ((Moviemade) activity.getApplication()).eventBus().send(new Events.MovieListUpdateAdult());
            } else if (position == aboutRow) {
                startActivity(new Intent(activity, AboutActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    private class SettingsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View cell;

            if (type == 1) {
                cell = new EmptyCell(activity);
            } else if (type == 2) {
                cell = new TextCell(activity);
            } else {
                cell = new TextDetailCell(activity);
            }

            return new Holder(cell);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.changeLayoutParams().setMode(EmptyCell.MODE_DEFAULT).setHeight(ScreenUtils.dp(12));
            } else if (type == 2) {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams().setHeight(ScreenUtils.dp(52));

                if (position == aboutRow) {
                    cell.setMode(TextCell.MODE_DEFAULT);
                    cell.setText(R.string.About);
                }
            } else {
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
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == -1) {
                return 1;
            } else if (position == aboutRow) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}