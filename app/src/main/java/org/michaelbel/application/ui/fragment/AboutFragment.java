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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Moviemade;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.ui.AboutActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.cell.EmptyCell;
import org.michaelbel.application.ui.view.cell.TextCell;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class AboutFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int forkGithubRow;
    private int rateGooglePlay;
    private int otherAppsRow;
    private int libsRow;
    private int helpRow;
    private int feedbackRow;
    private int emptyRow;

    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;

    private RecyclerListView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (AboutActivity) getActivity();

        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.binding.toolbarTitle.setText(R.string.About);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        rowCount = 0;
        infoRow = rowCount++;
        rateGooglePlay = rowCount++;
        forkGithubRow = rowCount++;
        libsRow = rowCount++;
        otherAppsRow = rowCount++;
        helpRow = rowCount++;
        feedbackRow = rowCount++;
        emptyRow = rowCount++;

        linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(new AboutAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            if (position == forkGithubRow) {
                Browser.openUrl(activity, Moviemade.GITHUB_URL);
            } else if (position == feedbackRow) {
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageInfo("org.telegram.messenger", 0);
                    if (packageInfo != null) {
                        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse(Moviemade.TELEGRAM_URL));
                        startActivity(telegram);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, Moviemade.EMAIL);
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Subject));
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(intent, getString(R.string.Feedback)));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    // todo Error
                }
            } else if (position == rateGooglePlay) {

            } else if (position == otherAppsRow) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Moviemade.ACCOUNT_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.openUrl(activity, Moviemade.ACCOUNT_WEB);
                }
            } else if (position == libsRow) {
                activity.startFragment(new LibsFragment(), activity.binding.fragmentLayout, "libsFragment");
            }
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
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
                    cell.setText(AndroidUtils.replaceTags(getString(R.string.ProjectInfo, getString(R.string.AppName))));
                } else if (position == emptyRow) {
                    cell.setMode(EmptyCell.MODE_DEFAULT);
                    cell.setHeight(ScreenUtils.dp(12));
                }
            } else if (type == 2) {
                TextCell cell = (TextCell) holder.itemView;
                cell.changeLayoutParams()
                    .setMode(TextCell.MODE_ICON)
                    .setHeight(ScreenUtils.dp(52));

                if (position == rateGooglePlay) {
                    cell.setIcon(R.drawable.ic_google_play)
                        .setText(R.string.RateGooglePlay)
                        .setDivider(true);
                } else if (position == forkGithubRow) {
                    cell.setIcon(R.drawable.ic_github)
                        .setText(R.string.ForkGithub)
                        .setDivider(true);
                } else if (position == libsRow) {
                    cell.setIcon(R.drawable.ic_storage)
                        .setText(R.string.OpenSourceLibs)
                        .setDivider(true);
                } else if (position == otherAppsRow) {
                    cell.setIcon(R.drawable.ic_shop)
                        .setText(R.string.OtherDeveloperApps);
                } else if (position == feedbackRow) {
                    cell.setIcon(R.drawable.ic_mail)
                        .setText(R.string.Feedback);
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
            iconImageView.setLayoutParams(LayoutHelper.makeLinear(120, 120, Gravity.CENTER_HORIZONTAL));
            addView(iconImageView);

            nameTextView = new TextView(context);
            nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            nameTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            nameTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            nameTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 5, 0, 0));
            addView(nameTextView);

            versionTextView = new TextView(context);
            versionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            versionTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            versionTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 4, 0, 0));
            addView(versionTextView);

            setVersion();
        }

        private void setVersion() {
            try {
                PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                nameTextView.setText(getString(R.string.AppForAndroid, getString(R.string.AppName)));
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