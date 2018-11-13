package org.michaelbel.moviemade.modules_beta.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.material.widget.Holder;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.modules_beta.view.cell.ThemeCell;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Date: Ср, Март 07 2018
 * Time: 18:52 MSK
 *
 * @author Michael Bel
 */

public class FilterView extends FrameLayout {

    private List<String> filters = new ArrayList<>();

    private TextView titleView;
    private CendingButton view2;
    private RecyclerListView recyclerView;

    private OnFilterChanged listener;

    public interface OnFilterChanged {
        void onFilterChange(int filter);
    }

    public FilterView(@NonNull Context context) {
        super(context);

        filters.add("Default");
        filters.add("Title");
        filters.add("Release Date");
        filters.add("Popularity");
        filters.add("Vote Average");

        FilterAdapter adapter = new FilterAdapter();

        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        titleView = new TextView(context);
        titleView.setLines(1);
        titleView.setMaxLines(1);
        titleView.setSingleLine();
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleView.setText("Sort by");
        titleView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 20, 16, 0, 0));
        addView(titleView);

        view2 = new CendingButton(context);
        view2.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END, 0, 8, 24, 0));
        addView(view2);

        recyclerView = new RecyclerListView(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 48, 0, 0));
        recyclerView.setOnItemClickListener((view, position) -> {
            SharedPreferences prefs = context.getSharedPreferences("mainconfig", AppCompatActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("watchlistFilter", position);
            editor.apply();

            listener.onFilterChange(position);
        });
        addView(recyclerView);
    }

    public void setListener(OnFilterChanged listener) {
        this.listener = listener;
    }

    private class FilterAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Holder(new ThemeCell(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            String item = filters.get(position);

            ThemeCell view = (ThemeCell) holder.itemView;
            view.setText(item);
            //view.setIcon();
        }

        @Override
        public int getItemCount() {
            return filters != null ? filters.size() : 0;
        }
    }
}