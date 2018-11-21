package org.michaelbel.moviemade.ui.modules.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.modules.about.AboutActivity;
import org.michaelbel.moviemade.ui.modules.about.Source;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.Browser;

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

public class LibsFragment extends Fragment {

    private LibsAdapter adapter;
    private AboutActivity activity;

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

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.VERTICAL, false));
        recyclerView.setOnItemClickListener((v, position) -> Browser.INSTANCE.openUrl(activity, adapter.sources.get(position).getUrl()));
    }

    public class LibsAdapter extends RecyclerView.Adapter<LibsAdapter.LibsViewHolder> {

        private List<Source> sources = new ArrayList<>();

        @NonNull
        @Override
        public LibsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_details, parent, false);
            return new LibsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LibsViewHolder holder, int position) {
            Source source = sources.get(position);

            holder.textView.setText(source.getName());
            holder.valueView.setText(source.getLicense());
            holder.dividerView.setVisibility(position != sources.size() - 1 ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return sources != null ? sources.size() : 0;
        }

        private void addSource(String name, String url, String license) {
            sources.add(new Source(name, url, license));
            notifyItemInserted(sources.size() - 1);
        }

        class LibsViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.text_view) AppCompatTextView textView;
            @BindView(R.id.value_text) AppCompatTextView valueView;
            @BindView(R.id.divider_view) View dividerView;

            private LibsViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}