package org.michaelbel.moviemade.presentation.features.about;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.michaelbel.moviemade.BuildConfig;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.presentation.base.BaseFragment;
import org.michaelbel.moviemade.core.utils.Browser;
import org.michaelbel.moviemade.core.utils.LinksKt;
import org.michaelbel.moviemade.core.utils.SpannableUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.internal.DebouncingOnClickListener;

@Deprecated
public class AboutFragment extends BaseFragment {

    private static final String LIBS_FRAGMENT_TAG = "libsFragment";
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
        activity.getToolbar().setNavigationOnClickListener(v -> activity.finish());
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setTitle(R.string.about);
        }

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

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new AboutAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private void doAction(int position) {
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
            activity.startFragment(new LibsFragment(), R.id.container, LIBS_FRAGMENT_TAG);
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
    }

    public class AboutAdapter extends RecyclerView.Adapter {

        private ImageView iconView;
        private AppCompatTextView textView;
        private AppCompatTextView appNameText;
        private AppCompatTextView versionText;
        private AppCompatTextView poweredText;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_about, parent, false);
                HeaderVH holder = new HeaderVH(view);
                appNameText = view.findViewById(R.id.appName);
                versionText = view.findViewById(R.id.versionText);
                view.setOnClickListener(new DebouncingOnClickListener() {
                    @Override
                    public void doClick(View v) {
                        int position = holder.getAdapterPosition();
                        doAction(position);
                    }
                });
                return holder;
            } else if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_powered, parent, false);
                AboutVH holder = new AboutVH(view);
                poweredText = view.findViewById(R.id.poweredText);
                view.setOnClickListener(new DebouncingOnClickListener() {
                    @Override
                    public void doClick(View v) {
                        int position = holder.getAdapterPosition();
                        doAction(position);
                    }
                });
                return holder;
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell, parent, false);
                FooterVH holder = new FooterVH(view);
                iconView = view.findViewById(R.id.icon_view);
                textView = view.findViewById(R.id.text_view);
                view.setOnClickListener(new DebouncingOnClickListener() {
                    @Override
                    public void doClick(View v) {
                        int position = holder.getAdapterPosition();
                        doAction(position);
                    }
                });
                return holder;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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

        class HeaderVH extends RecyclerView.ViewHolder {
            HeaderVH(View itemView) {
                super(itemView);
            }
        }

        class AboutVH extends RecyclerView.ViewHolder {

             AboutVH(View itemView) {
                super(itemView);
            }
        }

        class FooterVH extends RecyclerView.ViewHolder {
            FooterVH(View itemView) {
                super(itemView);
            }
        }
    }
}