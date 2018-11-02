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

import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.annotation.OptimizedForTablets;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@OptimizedForTablets
public class SettingsFragment extends Fragment {

    private int rowCount;
    private int inAppBrowserRow;
    private int adultRow;
    private int aboutRow;

    private SettingsAdapter adapter;
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

        recyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rowCount = 0;
        inAppBrowserRow = rowCount++;
        adultRow = rowCount++;
        aboutRow = rowCount++;

        adapter = new SettingsAdapter();
        prefs = activity.getSharedPreferences("mainconfig", Activity.MODE_PRIVATE);
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adapter);
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
            View view;

            if (type == 1) {
                view = new TextCell(activity);
            } else {
                view = new TextDetailCell(activity);
            }

            return new org.michaelbel.moviemade.ui.widget.RecyclerListView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                TextCell cell = (TextCell) holder.itemView;
                cell.setHeight(ScreenUtils.dp(52));

                if (position == aboutRow) {
                    cell.setMode(TextCell.MODE_DEFAULT);
                    cell.setText(R.string.about);
                }
            } else {
                TextDetailCell cell = (TextDetailCell) holder.itemView;

                if (position == inAppBrowserRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(R.string.in_app_browser);
                    cell.setValue(R.string.in_app_browser_info);
                    cell.setChecked(prefs.getBoolean("in_app_browser", true));
                    cell.setDivider(true);
                } else if (position == adultRow) {
                    cell.setMode(TextDetailCell.MODE_SWITCH);
                    cell.setText(getString(R.string.include_adult));
                    cell.setValue(R.string.include_adult_info);
                    cell.setChecked(prefs.getBoolean("adult", true));
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
            } else {
                return 2;
            }
        }
    }

    /*private class SettingsAdapter extends RecyclerView.Adapter {

        private AppCompatTextView textView;

        private AppCompatTextView cellText1;
        private AppCompatTextView cellText2;
        private SwitchCompat switchCompat;
        private View dividerView;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View view;

            if (type == 1) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_text, parent, false);
                textView = view.findViewById(R.id.text_view);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_details_switch, parent, false);
                cellText1 = view.findViewById(R.id.text_view);
                cellText2 = view.findViewById(R.id.value_text);
                switchCompat = view.findViewById(R.id.switch_compat);
                dividerView = view.findViewById(R.id.divider_view);
            }

            return new org.michaelbel.moviemade.ui.widget.RecyclerListView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                textView.setText(R.string.about);
            } else {
                if (position == inAppBrowserRow) {
                    cellText1.setText(R.string.in_app_browser);
                    cellText2.setText(R.string.in_app_browser_info);
                    switchCompat.setChecked(prefs.getBoolean("in_app_browser", true));
                    dividerView.setVisibility(View.VISIBLE);
                } else if (position == adultRow) {
                    cellText1.setText(getString(R.string.include_adult));
                    cellText2.setText(R.string.include_adult_info);
                    switchCompat.setChecked(prefs.getBoolean("adult", true));
                    dividerView.setVisibility(View.VISIBLE);
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
            } else {
                return 2;
            }
        }
    }*/
}