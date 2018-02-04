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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.michaelbel.bottomsheetdialog.BottomSheet;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.model.Source;
import org.michaelbel.moviemade.ui.AboutActivity;
import org.michaelbel.moviemade.ui.adapter.recycler.Holder;
import org.michaelbel.moviemade.ui.view.cell.TextDetailCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

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
        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.binding.toolbarTitle.setText(R.string.OpenSourceLibs);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

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
        //sources.add(new Source("Realm Android Adapters", "https://github.com/realm/realm-android-adapters", "Apache License 2.0"));
        //sources.add(new Source("Dagger 2", "https://github.com/google/dagger", "Apache License v2.0"));
        //sources.add(new Source("CircleIndicator", "https://github.com/ongakuer/circleindicator", "Apache License 2.0"));
        //sources.add(new Source("RxAndroid", "https://github.com/reactivex/rxjava","Apache License 2.0"));

        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new LibsAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view, position) -> Browser.openUrl(activity, sources.get(position).url));
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
                    AndroidUtils.copyToClipboard(sources.get(position).url);
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

    private class LibsAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new TextDetailCell(activity));
            //return new Holder(new TextCell(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Source source = sources.get(position);

            TextDetailCell cell = (TextDetailCell) holder.itemView;
            cell.changeLayoutParams()
                .setText(source.name)
                .setValue(source.license)
                .setDivider(position != sources.size() - 1);

            /*TextCell cell = (TextCell) holder.itemView;
            cell.changeLayoutParams()
                .setHeight(ScreenUtils.dp(52))
                .setText(source.name)
                .setDivider(position != sources.size() - 1);*/
        }

        @Override
        public int getItemCount() {
            return sources != null ? sources.size() : 0;
        }
    }
}