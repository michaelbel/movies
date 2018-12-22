package org.michaelbel.moviemade.ui.modules.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.eventbus.Events;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.about.AboutActivity;
import org.michaelbel.moviemade.ui.modules.settings.cell.TextCell;
import org.michaelbel.moviemade.ui.modules.settings.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.widgets.ExpandableView;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.SharedPrefsKt;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class SettingsFragment extends BaseFragment {

    private int rowCount;
    private int inAppBrowserRow;
    private int adultRow;
    private int adsRow;
    private int aboutRow;

    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;

    @Inject SharedPreferences sharedPreferences;

    @BindView(R.id.recycler_view) RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SettingsActivity) getActivity();
        Moviemade.get(activity).getComponent().injest(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setNavigationOnClickListener(v -> activity.finish());
        activity.getToolbarTitle().setText(R.string.settings);

        rowCount = 0;
        inAppBrowserRow = rowCount++;
        adultRow = rowCount++;
        adsRow = rowCount++;
        aboutRow = rowCount++;

        SettingsAdapter adapter = new SettingsAdapter();
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> {
            if (position == inAppBrowserRow) {
                boolean enable = sharedPreferences.getBoolean(SharedPrefsKt.KEY_BROWSER, true);
                sharedPreferences.edit().putBoolean(SharedPrefsKt.KEY_BROWSER, !enable).apply();
                if (v instanceof TextDetailCell) {
                    ((TextDetailCell) v).setChecked(!enable);
                }
            } else if (position == adultRow) {
                boolean enable = sharedPreferences.getBoolean(SharedPrefsKt.KEY_ADULT, true);
                sharedPreferences.edit().putBoolean(SharedPrefsKt.KEY_ADULT, !enable).apply();
                if (v instanceof TextDetailCell) {
                    ((TextDetailCell) v).setChecked(!enable);
                }
                ((Moviemade) activity.getApplication()).eventBus().send(new Events.MovieListUpdateAdult());
            } else if (position == aboutRow) {
                startActivity(new Intent(activity, AboutActivity.class));
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    private class SettingsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View view;

            if (type == 1) {
                view = new TextCell(activity);
            } else if (type == 2) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_settings_ads, parent, false);
            } else {
                view = new TextDetailCell(activity);
            }

            return new RecyclerListView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                TextCell cell = (TextCell) holder.itemView;
                cell.setHeight(DeviceUtil.INSTANCE.dp(holder.itemView.getContext(), 52));

                if (position == aboutRow) {
                    cell.setMode(TextCell.MODE_DEFAULT);
                    cell.setText(R.string.about);
                }
            } else if (type == 2) {
                ExpandableView view = (ExpandableView) holder.itemView;
                view.setTitle(R.string.disable_ads);
            } else {
                TextDetailCell cell = (TextDetailCell) holder.itemView;

                if (position == inAppBrowserRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.in_app_browser);
                    cell.setValue(R.string.in_app_browser_info);
                    cell.setChecked(sharedPreferences.getBoolean(SharedPrefsKt.KEY_BROWSER, true));
                    cell.setDivider(true);
                } else if (position == adultRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(getString(R.string.include_adult));
                    cell.setValue(R.string.include_adult_info);
                    cell.setChecked(sharedPreferences.getBoolean(SharedPrefsKt.KEY_ADULT, true));
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
            if (position == aboutRow) {
                return 1;
            } else if (position == adsRow) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}