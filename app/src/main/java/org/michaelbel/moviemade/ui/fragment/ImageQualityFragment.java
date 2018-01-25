package org.michaelbel.moviemade.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.SettingsActivity;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.ui.view.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;

public class ImageQualityFragment extends Fragment {

    private int rowCount;
    private int backdropRow;
    private int logoRow;
    private int posterRow;
    private int profileRow;
    private int stillRow;

    private ListAdapter adapter;
    private SharedPreferences prefs;
    private SettingsActivity activity;
    private LinearLayoutManager layoutManager;

    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SettingsActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.binding.toolbarTitle.setText(R.string.ImageQuality);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        rowCount = 0;
        backdropRow = rowCount++;
        logoRow = rowCount++;
        posterRow = rowCount++;
        profileRow = rowCount++;
        stillRow = rowCount++;

        adapter = new ListAdapter();
        layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        prefs = activity.getSharedPreferences("main_config", Context.MODE_PRIVATE);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            if (position == backdropRow) {
                builder.setTitle("Backdrop");

                String[] items = new String[] {
                        "w300", "w780", "w1280", "original"
                };

                builder.setItems(items, (dialogInterface, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality_backdrop", items[which]);
                    editor.putBoolean("image_quality_settings_customize", true);
                    editor.apply();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            } else if (position == logoRow) {
                builder.setTitle("Logo");

                String[] items = new String[] {
                        "w45", "w92", "w154", "w185", "w300", "w500", "original"
                };

                builder.setItems(items, (dialogInterface, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality_logo", items[which]);
                    editor.putBoolean("image_quality_settings_customize", true);
                    editor.apply();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            } else if (position == posterRow) {
                builder.setTitle("Poster");

                String[] items = new String[] {
                        "w92", "w154", "w185", "w342", "w500", "w780", "original"
                };

                builder.setItems(items, (dialogInterface, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality_poster", items[which]);
                    editor.putBoolean("image_quality_settings_customize", true);
                    editor.apply();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            } else if (position == profileRow) {
                builder.setTitle("Profile");

                String[] items = new String[] {
                        "w45", "w185", "w632", "original"
                };

                builder.setItems(items, (dialogInterface, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality_profile", items[which]);
                    editor.putBoolean("image_quality_settings_customize", true);
                    editor.apply();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            } else if (position == stillRow) {
                builder.setTitle("Still");

                String[] items = new String[] {
                        "w92", "w185", "w300", "original"
                };

                builder.setItems(items, (dialogInterface, which) -> {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("image_quality_still", items[which]);
                    editor.putBoolean("image_quality_settings_customize", true);
                    editor.apply();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
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
            } else if (type == 2) {
                TextDetailCell cell = (TextDetailCell) holder.itemView;
                cell.setMode(TextDetailCell.MODE_SWITCH);
                cell.changeLayoutParams();
            } else {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == backdropRow) {
                    String quality = prefs.getString("image_quality_backdrop", "w780");
                    cell.setMode(TextCell.MODE_VALUE_TEXT);
                    cell.setText("Backdrop");
                    cell.setValue(quality);
                    cell.setDivider(true);
                } else if (position == logoRow) {
                    String quality = prefs.getString("image_quality_logo", "w185");
                    cell.setMode(TextCell.MODE_VALUE_TEXT);
                    cell.setText("Logo");
                    cell.setValue(quality);
                    cell.setDivider(true);
                } else if (position == posterRow) {
                    String quality = prefs.getString("image_quality_poster", "w342");
                    cell.setMode(TextCell.MODE_VALUE_TEXT);
                    cell.setText("Poster");
                    cell.setValue(quality);
                    cell.setDivider(true);
                } else if (position == profileRow) {
                    String quality = prefs.getString("image_quality_profile", "w185");
                    cell.setMode(TextCell.MODE_VALUE_TEXT);
                    cell.setText("Profile");
                    cell.setValue(quality);
                    cell.setDivider(true);
                } else if (position == stillRow) {
                    String quality = prefs.getString("image_quality_still", "w185");
                    cell.setMode(TextCell.MODE_VALUE_TEXT);
                    cell.setText("Still");
                    cell.setValue(quality);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == -900) {
                return 0;
            } else if (position == -800) {
                return 1;
            } else if (position == -700) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}