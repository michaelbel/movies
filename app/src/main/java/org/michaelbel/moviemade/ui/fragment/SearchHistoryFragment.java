package org.michaelbel.moviemade.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.SettingsActivity;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.model.SearchItem;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.cell.SearchItemCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class SearchHistoryFragment extends Fragment {

    private SearchHistoryAdapter adapter;
    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<SearchItem> searches = new ArrayList<>();

    private EmptyView emptyView;
    private RecyclerListView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SettingsActivity) getActivity();

        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.binding.toolbarTitle.setText(R.string.SearchHistory);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = new EmptyView(activity);
        emptyView.setMode(EmptyView.MODE_NO_HISTORY);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        adapter = new SearchHistoryAdapter(searches);
        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> {
            //Intent intent = new Intent(activity, SearchActivity.class);
            //intent.putExtra("query", searches.get(position).queryTitle);
            //activity.finish();
            //startActivity(intent);
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            return true;
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSearchHistoryItems();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    private void loadSearchHistoryItems() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<SearchItem> items = realm.where(SearchItem.class).findAllAsync();
        searches.addAll(items);
        Collections.reverse(searches);
        adapter.notifyItemInserted(searches.size());
    }

    private long getSearchHistoryItems() {
        Realm realm = Realm.getDefaultInstance();
        long count = realm.where(SearchItem.class).count();
        return count;
    }

    private void removeSearchItem(int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            SearchItem searchItem = realm1.where(SearchItem.class).equalTo("queryDate", searches.get(position).queryDate).findFirstAsync();
            searchItem.deleteFromRealm();
        });

        //searches.remove(position);
        adapter.notifyDataSetChanged();
        //adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
    }

    public class SearchHistoryAdapter extends RecyclerView.Adapter {

        public List<SearchItem> searches;

        public SearchHistoryAdapter(List<SearchItem> searches) {
            this.searches = searches;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            SearchItemCell view = new SearchItemCell(parent.getContext());
            Holder viewHolder = new Holder(view);
            view.setOnIconClick(new SearchItemCell.DeleteIconClick() {
                @Override
                public void onIconClick(View view) {
                    removeSearchItem(viewHolder.getAdapterPosition());
                }
            });

            return viewHolder;
            //return new Holder(new SearchItemCell(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SearchItem item = searches.get(position);

            SearchItemCell cell = (SearchItemCell) holder.itemView;
            cell.setText(item.queryTitle)
                .setValue(item.queryDate)
                .setDivider(position != searches.size() - 1);
        }

        @Override
        public int getItemCount() {
            return searches != null ? searches.size() : 0;
        }
    }
}