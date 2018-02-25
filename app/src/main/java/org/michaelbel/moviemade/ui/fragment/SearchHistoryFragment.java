package org.michaelbel.moviemade.ui.fragment;

/*
public class SearchHistoryFragment extends Fragment {

    private SearchHistoryAdapter adapter;
    private SettingsActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<SearchItem> searches = new ArrayList<>();

    private EmptyView emptyView;
    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (SettingsActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTitle.setText(R.string.SearchHistory);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = new EmptyView(activity);
        emptyView.setMode(EmptyViewMode.MODE_NO_HISTORY);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        adapter = new SearchHistoryAdapter(searches);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

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
}*/