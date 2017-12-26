package org.michaelbel.application.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import org.michaelbel.application.util.ScreenUtils;
import org.michaelbel.bottomsheet.BottomSheet;

import java.util.ArrayList;
import java.util.List;

public class LibsFragment extends Fragment {

    private ListAdapter adapter;
    private AboutActivity activity;
    private LinearLayoutManager layoutManager;
    private List<Source> list = new ArrayList<>();

    private RecyclerListView recyclerView;

    private class Source {

        public String url;
        public String name;
        public String license;

        public Source(String name, String url, String license) {
            this.url = url;
            this.name = name;
            this.license = license;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AboutActivity) getActivity();

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finishFragment());
        activity.toolbarTextView.setText(R.string.OpenSourceLibs);

        list.add(new Source("BottomSheet", "https://github.com/michaelbel/bottomsheet","Apache License v2.0"));
        list.add(new Source("Retrofit", "https://square.github.io/retrofit","Apache License v2.0"));
        list.add(new Source("RxJava", "https://github.com/reactivex/rxjava","Apache License v2.0"));
        list.add(new Source("RxAndroid", "https://github.com/reactivex/rxjava","Apache License v2.0"));
        list.add(new Source("Realm Java", "https://github.com/realm/realm-java", "Apache License v2.0"));
        list.add(new Source("Glide", "https://bumptech.github.io/glide/","BSD, MIT and Apache License v2.0"));

        adapter = new ListAdapter();
        layoutManager = new LinearLayoutManager(activity);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Browser.openUrl(activity, list.get(position).url);
        });
        recyclerView.setOnItemLongClickListener((view, position) -> {
            BottomSheet.Builder builder = new BottomSheet.Builder(activity);
            builder.setTitle(list.get(position).url);
            builder.setTitleTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
            builder.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
            builder.setItemTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
            builder.setCellHeight(ScreenUtils.dp(52));
            builder.setItems(new int[] { R.string.Open, R.string.CopyLink }, (dialogInterface, i) -> {
                if (i == 0) {
                    Browser.openUrl(activity, list.get(position).url);
                } else if (i == 1) {
                    ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Link", list.get(position).url);
                    if (clipboard != null) {
                        clipboard.setPrimaryClip(clip);
                    }

                    Toast.makeText(activity, R.string.LinkCopied, Toast.LENGTH_SHORT).show();
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
        Parcelable state = layoutManager.onSaveInstanceState();
        layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.onRestoreInstanceState(state);
    }

    public class ListAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new TextDetailCell(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Source source = list.get(position);

            TextDetailCell cell = (TextDetailCell) holder.itemView;
            cell.changeLayoutParams();
            cell.setText(source.name);
            cell.setValue(source.license);
            cell.setDivider(position != list.size() - 1);
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }
    }
}