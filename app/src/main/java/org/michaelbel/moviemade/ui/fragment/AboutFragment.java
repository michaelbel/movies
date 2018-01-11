package org.michaelbel.moviemade.ui.fragment;

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

import org.michaelbel.moviemade.AboutActivity;
import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Moviemade;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.ui.adapter.Holder;
import org.michaelbel.moviemade.ui.view.cell.EmptyCell;
import org.michaelbel.moviemade.ui.view.cell.TextCell;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.ScreenUtils;

public class AboutFragment extends Fragment {

    private int rowCount;
    private int infoRow;
    private int forkGithubRow;
    private int rateGooglePlay;
    private int otherAppsRow;
    private int libsRow;
    private int helpRow;
    private int feedbackRow;
    private int shareFriendsRow;
    private int donatePaypalRow;
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
        //forkGithubRow = rowCount++;
        libsRow = rowCount++;
        otherAppsRow = rowCount++;
        helpRow = rowCount++;
        feedbackRow = rowCount++;
        shareFriendsRow = rowCount++;
        donatePaypalRow = rowCount++;
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
            } else if (position == rateGooglePlay) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Moviemade.APP_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.openUrl(activity, Moviemade.APP_WEB);
                }
            } else if (position == otherAppsRow) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(Moviemade.ACCOUNT_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.openUrl(activity, Moviemade.ACCOUNT_WEB);
                }
            } else if (position == libsRow) {
                activity.startFragment(new LibsFragment(), activity.binding.fragmentView, "libsFragment");
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
            } else if (position == shareFriendsRow) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, Moviemade.APP_WEB);
                startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
            } else if (position == donatePaypalRow) {
                Browser.openUrl(activity, Moviemade.PAYPAL_ME);
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
                        .setText(R.string.Feedback)
                        .setDivider(true);
                } else if (position == shareFriendsRow) {
                    cell.setIcon(R.drawable.ic_share)
                        .setText(R.string.ShareWithFriends)
                        .setDivider(true);
                } else if (position == donatePaypalRow) {
                    cell.setIcon(R.drawable.ic_cash_usd)
                        .setText("Donate PayPal");
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

        private ImageView launcherIcon;
        private TextView appNameText;
        private TextView versionText;

        public AboutView(Context context) {
            super(context);

            setOrientation(VERTICAL);
            setPadding(ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24));

            launcherIcon = new ImageView(context);
            launcherIcon.setImageResource(R.mipmap.ic_launcher);
            launcherIcon.setLayoutParams(LayoutHelper.makeLinear(120, 120, Gravity.CENTER_HORIZONTAL));
            addView(launcherIcon);

            appNameText = new TextView(context);
            appNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            appNameText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            appNameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            appNameText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 5, 0, 0));
            addView(appNameText);

            versionText = new TextView(context);
            versionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            versionText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
            versionText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 4, 0, 0));
            addView(versionText);

            setVersion();
        }

        private void setVersion() {
            try {
                int versionCode = BuildConfig.VERSION_CODE;
                String versionName = BuildConfig.VERSION_NAME;
                String versionDate = BuildConfig.VERSION_DATE;

                appNameText.setText(getString(R.string.AppForAndroid, getString(R.string.AppNameBeta)));
                versionText.setText(getString(R.string.VersionBuildDate, versionName, versionCode, versionDate));
            } catch (Exception e) {
                // todo Error
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