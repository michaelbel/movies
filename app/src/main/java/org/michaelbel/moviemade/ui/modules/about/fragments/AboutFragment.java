package org.michaelbel.moviemade.ui.modules.about.fragments;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.modules.about.AboutActivity;
import org.michaelbel.moviemade.ui.widgets.RecyclerListView;
import org.michaelbel.moviemade.utils.Browser;
import org.michaelbel.moviemade.utils.LinksKt;
import org.michaelbel.moviemade.utils.SpannableUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AboutFragment extends Fragment {

    private static final String LIBS_FRAGMENT_TAG = "libs_fragment";
    private static final String TELEGRAM_PACKAGE_NAME = "org.telegram.messenger";

    private int rowCount;
    private int infoRow;
    private int forkGithubRow;
    private int rateGooglePlay;
    private int otherAppsRow;
    private int libsRow;
    private int feedbackRow;
    private int shareFriendsRow;
    private int donatePaypalRow;
    private int poweredByRow;

    private Unbinder unbinder;
    private AboutActivity activity;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.recycler_view) public RecyclerListView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AboutActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this, view);

        activity.toolbar.setNavigationOnClickListener(v -> activity.finish());
        activity.toolbarTitle.setText(R.string.about);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rowCount = 0;
        infoRow = rowCount++;
        rateGooglePlay = rowCount++;
        forkGithubRow = rowCount++;
        libsRow = rowCount++;
        otherAppsRow = rowCount++;
        feedbackRow = rowCount++;
        shareFriendsRow = rowCount++;
        donatePaypalRow = rowCount++;
        poweredByRow = rowCount++;

        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(new AboutAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setOnItemClickListener((v, position) -> {
            if (position == forkGithubRow) {
                Browser.INSTANCE.openUrl(activity, LinksKt.GITHUB_URL);
            } else if (position == rateGooglePlay) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(LinksKt.APP_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.INSTANCE.openUrl(activity, LinksKt.APP_WEB);
                }
            } else if (position == otherAppsRow) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(LinksKt.ACCOUNT_MARKET));
                    startActivity(intent);
                } catch (Exception e) {
                    Browser.INSTANCE.openUrl(activity, LinksKt.ACCOUNT_WEB);
                }
            } else if (position == libsRow) {
                activity.startFragment(new LibsFragment(), R.id.fragment_view, LIBS_FRAGMENT_TAG);
            } else if (position == feedbackRow) {
                try {
                    PackageManager packageManager = activity.getPackageManager();
                    PackageInfo packageInfo = packageManager.getPackageInfo(TELEGRAM_PACKAGE_NAME, 0);
                    if (packageInfo != null) {
                        Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse(LinksKt.TELEGRAM_URL));
                        startActivity(telegram);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, LinksKt.EMAIL);
                        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(Intent.createChooser(intent, getString(R.string.feedback)));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (position == shareFriendsRow) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, LinksKt.APP_WEB);
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
            } else if (position == donatePaypalRow) {
                Browser.INSTANCE.openUrl(activity, LinksKt.PAYPAL_ME);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Parcelable state = linearLayoutManager.onSaveInstanceState();
        linearLayoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.onRestoreInstanceState(state);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.AboutViewHolder> {

        ImageView iconView;
        AppCompatTextView textView;
        AppCompatTextView appNameText;
        AppCompatTextView versionText;
        AppCompatTextView poweredText;

        @NonNull
        @Override
        public AboutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;

            if (viewType == 0) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_about, parent, false);
                appNameText = view.findViewById(R.id.app_name_text);
                versionText = view.findViewById(R.id.version_text);
            } else if (viewType == 1) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_powered, parent, false);
                poweredText = view.findViewById(R.id.powered_text);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell, parent, false);
                iconView = view.findViewById(R.id.icon_view);
                textView = view.findViewById(R.id.text_view);
            }

            return new AboutViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AboutViewHolder holder, int position) {
            int type = getItemViewType(position);

            if (type == 0) {
                appNameText.setText(getString(R.string.app_for_android, getString(R.string.app_name)));
                versionText.setText(getString(R.string.version_build_date, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, BuildConfig.VERSION_DATE));
            } else if (type == 1) {
                poweredText.setText(SpannableUtil.replaceTags(getString(R.string.powered_by)));
            } else {
                if (position == rateGooglePlay) {
                    iconView.setImageResource(R.drawable.ic_google_play);
                    textView.setText(R.string.rate_google_play);
                } else if (position == forkGithubRow) {
                    iconView.setImageResource(R.drawable.ic_github);
                    textView.setText(R.string.fork_github);
                } else if (position == libsRow) {
                    iconView.setImageResource(R.drawable.ic_storage);
                    textView.setText(R.string.open_source_libs);
                } else if (position == otherAppsRow) {
                    iconView.setImageResource(R.drawable.ic_shop);
                    textView.setText(R.string.other_developer_apps);
                } else if (position == feedbackRow) {
                    iconView.setImageResource(R.drawable.ic_mail);
                    textView.setText(R.string.feedback);
                } else if (position == shareFriendsRow) {
                    iconView.setImageResource(R.drawable.ic_share);
                    textView.setText(R.string.share_with_friends);
                } else if (position == donatePaypalRow) {
                    iconView.setImageResource(R.drawable.ic_paypal);
                    textView.setText(R.string.donate_paypal);
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
            } else if (position == poweredByRow) {
                return 1;
            } else {
                return 2;
            }
        }

        class AboutViewHolder extends RecyclerView.ViewHolder {

             AboutViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}