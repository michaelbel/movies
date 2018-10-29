package org.michaelbel.moviemade.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.michaelbel.bottomsheet.BottomSheet;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.Holder;
import org.michaelbel.material.widget.RecyclerListView;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.model.Source;
import org.michaelbel.moviemade.ui_old.view.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.activity.AboutActivity;

import java.util.ArrayList;
import java.util.List;

public class LibsFragment extends Fragment {

    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<Source> sources = new ArrayList<>();

    private RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AboutActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_libs, container, false);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(v -> activity.finishFragment());
        activity.toolbarTitle.setText(R.string.OpenSourceLibs);

        sources.add(new Source("BottomSheet", "https://github.com/michaelbel/bottomsheet","Apache License 2.0"));
        sources.add(new Source("Gson", "https://github.com/google/gson","Apache License 2.0"));
        sources.add(new Source("Retrofit", "https://square.github.io/retrofit","Apache License 2.0"));
        sources.add(new Source("RxJava", "https://github.com/reactivex/rxjava","Apache License 2.0"));
        sources.add(new Source("Picasso", "https://square.github.io/picasso","Apache License 2.0"));
        sources.add(new Source("Realm Java", "https://github.com/realm/realm-java", "Apache License 2.0"));
        sources.add(new Source("Moxy", "https://github.com/arello-mobile/moxy", "The MIT License (MIT)"));
        sources.add(new Source("GestureViews", "https://github.com/alexvasilkov/gestureviews", "Apache License 2.0"));
        sources.add(new Source("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager", "Apache License 2.0"));
        sources.add(new Source("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview", "Apache License 2.0"));
        sources.add(new Source("Android Animated Menu Items", "https://github.com/adonixis/android-animated-menu-items", "Apache License 2.0"));

        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new LibsAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> Browser.openUrl(activity, sources.get(position).url));
        recyclerView.setOnItemLongClickListener((v, position) -> {
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
        });

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    private class LibsAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            return new Holder(new TextDetailCell(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Source source = sources.get(position);

            TextDetailCell cell = (TextDetailCell) holder.itemView;
            cell.changeLayoutParams()
                .setText(source.name)
                .setValue(source.license)
                .setDivider(position != sources.size() - 1);
        }

        @Override
        public int getItemCount() {
            return sources != null ? sources.size() : 0;
        }
    }
}