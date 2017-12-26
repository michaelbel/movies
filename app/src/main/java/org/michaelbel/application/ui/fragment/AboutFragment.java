package org.michaelbel.application.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.ui.AboutActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.cell.EmptyCell;
import org.michaelbel.application.ui.view.cell.TextCell;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.ScreenUtils;

public class AboutFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int forkGithubRow;
    private int libsRow;
    private int helpRow;
    private int feedbackRow;
    private int emptyRow;

    /*private int rateGooglePlayRow;
    private int shareFriendsRow;
    private int supportDevRow;
    private int translationsRow;
    private int analyticsInfoRow;
    private int analyticsRow;*/

    private AboutAdapter adapter;
    private AboutActivity activity;
    private LinearLayoutManager layoutManager;

    private RecyclerListView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AboutActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_about, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.toolbarTextView.setText(R.string.About);

        rowCount = 0;
        infoRow = rowCount++;
        forkGithubRow = rowCount++;
        libsRow = rowCount++;
        helpRow = rowCount++;
        feedbackRow = rowCount++;
        emptyRow = rowCount++;

        adapter = new AboutAdapter();
        layoutManager = new LinearLayoutManager(activity);

        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnItemClickListener((view1, position) -> {
            if (position == forkGithubRow) {
                Browser.openUrl(activity, "https://github.com/michaelbel/moviemade");
            } else if (position == feedbackRow) {
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageInfo("org.telegram.messenger", 0);
                    if (packageInfo != null) {
                        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://t.me/michaelbel"));
                        startActivity(telegram);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, "michael-bel@outlook.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(intent, "Feedback"));
                    }
                } catch (PackageManager.NameNotFoundException e) {

                }
            } else if (position == libsRow) {
                activity.startFragment(new LibsFragment(), "libsFragment");
            }
        });

        return fragmentView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = layoutManager.onSaveInstanceState();
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.onRestoreInstanceState(state);
    }

    private class AboutAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            View view;

            if (type == 0) {
                view = new AboutView(activity);
            } else if (type == 1) {
                view = new EmptyCell(activity);
            } else {
                view = new TextCell(activity);
            }

            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 1) {
                EmptyCell cell = (EmptyCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == helpRow) {
                    cell.setMode(EmptyCell.MODE_TEXT);
                    cell.setText(getString(R.string.ProjectInfo, getString(R.string.AppName)));
                } else if (position == emptyRow) {
                    cell.setMode(EmptyCell.MODE_DEFAULT);
                    cell.setHeight(ScreenUtils.dp(12));
                }
            } else if (type == 2) {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams();

                if (position == forkGithubRow) {
                    cell.setText(R.string.ForkGithub);
                    cell.setDivider(true);
                } else if (position == libsRow) {
                    cell.setHeight(ScreenUtils.dp(52));
                    cell.setText(R.string.OpenSourceLibs);
                }  else if (position == feedbackRow) {
                    cell.setHeight(ScreenUtils.dp(52));
                    cell.setText(R.string.Feedback);
                }
            }
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == infoRow) {
                return 0;
            } else if (position == helpRow || position == emptyRow) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    public class AboutView extends LinearLayout {

        private ImageView iconImageView;
        private TextView nameTextView;
        private TextView versionTextView;

        public AboutView(Context context) {
            super(context);

            setOrientation(VERTICAL);
            setPadding(ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24));

            iconImageView = new ImageView(context);
            iconImageView.setImageResource(R.mipmap.ic_launcher);
            iconImageView.setLayoutParams(LayoutHelper.makeLinear(110, 110, Gravity.CENTER_HORIZONTAL));
            addView(iconImageView);

            nameTextView = new TextView(context);
            nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            nameTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            nameTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            nameTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 5, 0, 0));
            addView(nameTextView);

            versionTextView = new TextView(context);
            versionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            versionTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            versionTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT,
                    LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 4, 0, 0));
            addView(versionTextView);

            setVersion();
        }

        private void setVersion() {
            try {
                PackageInfo packageInfo = getContext().getPackageManager()
                        .getPackageInfo(getContext().getPackageName(), 0);
                nameTextView.setText(getString(R.string.AppNameForAndroid, getString(R.string.AppName)));
                versionTextView.setText(getString(R.string.VersionBuild, packageInfo.versionName, packageInfo.versionCode));
            } catch (Exception e) {
                // todo report
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
            int height = getMeasuredHeight();
            setMeasuredDimension(width, height);
        }
    }
}