package org.michaelbel.moviemade.presentation.features.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.utils.Browser;
import org.michaelbel.moviemade.presentation.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.internal.DebouncingOnClickListener;

public class LibsFragment extends BaseFragment {

    private class Source {
        private String name;
        private String url;
        private String license;

        public Source(String name, String url, String license) {
            this.name = name;
            this.url = url;
            this.license = license;
        }

        public String getName() {
            return name;
        }

        String getUrl() {
            return url;
        }

        public String getLicense() {
            return license;
        }
    }

    private AboutActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AboutActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setNavigationOnClickListener(v -> activity.finishFragment());
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(R.string.open_source_libs);
        }

        // FIXME add all sources.
        LibsAdapter adapter = new LibsAdapter();
        adapter.addSource("BottomSheet", "https://github.com/michaelbel/bottomsheet","Apache License 2.0");
        adapter.addSource("Gson", "https://github.com/google/gson","Apache License 2.0");
        adapter.addSource("Retrofit", "https://square.github.io/retrofit","Apache License 2.0");
        adapter.addSource("RxJava", "https://github.com/reactivex/rxjava","Apache License 2.0");
        adapter.addSource("Picasso", "https://square.github.io/picasso","Apache License 2.0");
        adapter.addSource("GestureViews", "https://github.com/alexvasilkov/gestureviews", "Apache License 2.0");
        adapter.addSource("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager", "Apache License 2.0");
        adapter.addSource("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview", "Apache License 2.0");
        adapter.addSource("Android Animated Menu Items", "https://github.com/adonixis/android-animated-menu-items", "Apache License 2.0");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void onSourceClick(Source source) {
        Browser.INSTANCE.openUrl(activity, source.getUrl());
    }

    public class LibsAdapter extends RecyclerView.Adapter<LibsAdapter.LibsViewHolder> {

        private List<LibsFragment.Source> sources = new ArrayList<>();

        private void addSource(String name, String url, String license) {
            sources.add(new Source(name, url, license));
            notifyItemInserted(sources.size() - 1);
        }

        @NonNull
        @Override
        public LibsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell_details, parent, false);
            LibsViewHolder viewHolder = new LibsViewHolder(view);
            view.setOnClickListener(new DebouncingOnClickListener() {
                @Override
                public void doClick(View v) {
                    int position = viewHolder.getAdapterPosition();
                    onSourceClick(sources.get(position));
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull LibsViewHolder holder, int position) {
            Source source = sources.get(position);
            holder.textView.setText(source.getName());
            holder.valueView.setText(source.getLicense());
            holder.dividerView.setVisibility(position != sources.size() - 1 ? View.VISIBLE : View.GONE);
        }

        @Override
        public int getItemCount() {
            return sources.size();
        }

        class LibsViewHolder extends RecyclerView.ViewHolder {

            AppCompatTextView textView;
            AppCompatTextView valueView;
            View dividerView;

            private LibsViewHolder(View view) {
                super(view);
                textView = view.findViewById(R.id.text_view);
                valueView = view.findViewById(R.id.value_text);
                dividerView = view.findViewById(R.id.divider_view);
            }
        }
    }
}