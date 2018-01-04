package org.michaelbel.application.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.ui.AboutActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.cell.TextDetailCell;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.AndroidUtilsDev;
import org.michaelbel.application.util.ScreenUtils;
import org.michaelbel.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class LibsFragment extends Fragment {

    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;
    private List<Source> sources = new ArrayList<>();

    private RecyclerListView recyclerView;

    private class Source {

        public String url;
        public String name;
        public String license;

        Source(String name, String url, String license) {
            this.url = url;
            this.name = name;
            this.license = license;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AboutActivity) getActivity();

        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.binding.toolbarTitle.setText(R.string.OpenSourceLibs);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        sources.add(new Source("BottomSheet", "https://github.com/michaelbel/bottomsheet","Apache License v2.0"));
        sources.add(new Source("Gson", "https://github.com/google/gson","Apache License v2.0"));
        sources.add(new Source("Retrofit", "https://square.github.io/retrofit","Apache License v2.0"));
        sources.add(new Source("RxJava", "https://github.com/reactivex/rxjava","Apache License v2.0"));
        sources.add(new Source("RxAndroid", "https://github.com/reactivex/rxjava","Apache License v2.0"));
        sources.add(new Source("Picasso", "https://square.github.io/picasso","Apache License v2.0"));
        sources.add(new Source("Dagger 2", "https://github.com/google/dagger", "Apache License v2.0"));
        sources.add(new Source("Realm Java", "https://github.com/realm/realm-java", "Apache License v2.0"));
        sources.add(new Source("Realm Android Adapters", "https://github.com/realm/realm-android-adapters", "Apache License v2.0"));
        sources.add(new Source("GestureViews", "https://github.com/alexvasilkov/gestureviews", "Apache License v2.0"));
        sources.add(new Source("CircleIndicator", "https://github.com/ongakuer/circleindicator", "Apache License v2.0"));
        sources.add(new Source("ChipsLayoutManager", "https://github.com/beloos/chipslayoutmanager", "Apache License v2.0"));
        sources.add(new Source("ExpandableTextView", "https://github.com/blogcat/android-expandabletextview", "Apache License v2.0"));

        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new LibsAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtilsDev.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) ->
                Browser.openUrl(activity, sources.get(position).url)
        );
        recyclerView.setOnItemLongClickListener((view, position) -> {
            BottomSheet.Builder builder = new BottomSheet.Builder(activity);
            builder.setTitle(sources.get(position).url);
            builder.setTitleMultiline(true);
            builder.setCellHeight(ScreenUtils.dp(52));
            builder.setTitleTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
            builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
            builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
            builder.setItems(new int[] { R.string.Open, R.string.CopyLink }, (dialogInterface, i) -> {
                if (i == 0) {
                    Browser.openUrl(activity, sources.get(position).url);
                } else if (i == 1) {
                    AndroidUtils.addToClipboard("Link", sources.get(position).url);
                    Toast.makeText(activity, getString(R.string.ClipboardCopied, getString(R.string.Link)), Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
            return true;
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    public class LibsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new TextDetailCell(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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