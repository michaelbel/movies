package org.michaelbel.moviemade.ui.modules.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.browser.Browser;
import org.michaelbel.moviemade.ui.modules.about.AboutActivity;
import org.michaelbel.moviemade.ui.modules.about.Source;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("all")
public class LibsFragment extends Fragment {

    private LibsAdapter adapter;
    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.recycler_view)
    public RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AboutActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_libs, container, false);
        ButterKnife.bind(this, view);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(v -> activity.finishFragment());
        activity.toolbarTitle.setText(R.string.open_source_libs);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new LibsAdapter();
        adapter.addSource("BottomSheet", "https://github.com/michaelbel/bottomsheet","Apache License 2.0");
        adapter.addSource("Gson", "https://github.com/google/gson","Apache License 2.0");
        adapter.addSource("Retrofit", "https://square.github.io/retrofit","Apache License 2.0");
        adapter.addSource("RxJava", "https://github.com/reactivex/rxjava","Apache License 2.0");
        adapter.addSource("Picasso", "https://square.github.io/picasso","Apache License 2.0");
        adapter.addSource("Realm Java", "https://github.com/realm/realm-java", "Apache License 2.0");
        adapter.addSource("Moxy", "https://github.com/arello-mobile/moxy", "The MIT License (MIT)");
        adapter.addSource("GestureViews", "https://github.com/alexvasilkov/gestureviews", "Apache License 2.0");
        adapter.addSource("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager", "Apache License 2.0");
        adapter.addSource("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview", "Apache License 2.0");
        adapter.addSource("Android Animated Menu Items", "https://github.com/adonixis/android-animated-menu-items", "Apache License 2.0");

        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> Browser.openUrl(activity, adapter.sources.get(position).getUrl()));
        /*recyclerView.setOnItemLongClickListener((v, position) -> {
            BottomSheet.Builder builder = new BottomSheet.Builder(activity);
            builder.setCellHeight(Extensions.dp(activity, 52));
            builder.setTitle(sources.get(position).url).setTitleMultiline(true);
            builder.setTitleTextColorRes(R.color.secondaryText);
            builder.setBackgroundColorRes(R.color.primary);
            builder.setItemTextColorRes(R.color.primaryText);
            builder.setItems(new int[] { R.string.Open, R.string.CopyLink }, (dialogInterface, i) -> {
                if (i == 0) {
                    Browser.openUrl(activity, sources.get(position).url);
                } else if (i == 1) {
                    Extensions.copyToClipboard(activity, sources.get(position).url);
                    Toast.makeText(activity, getString(R.string.ClipboardCopied, getString(R.string.Link)), Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
            return true;
        });*/
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }*/

    public class LibsAdapter extends RecyclerView.Adapter {

        private List<Source> sources = new ArrayList<>();

        @BindView(R.id.text_view)
        public AppCompatTextView textView;

        @BindView(R.id.value_text)
        public AppCompatTextView valueView;

        @BindView(R.id.divider_view)
        public View dividerView;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_details, parent, false);
            ButterKnife.bind(this, view);
            return new RecyclerListView.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Source source = sources.get(position);

            textView.setText(source.getName());
            valueView.setText(source.getLicense());
            dividerView.setVisibility(position != sources.size() - 1 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return sources != null ? sources.size() : 0;
        }

        private void addSource(String name, String url, String license) {
            sources.add(new Source(name, url, license));
            notifyItemInserted(sources.size() - 1);
        }
    }
}