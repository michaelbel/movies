package org.michaelbel.moviemade.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ReviewActivity;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.view.widget.GestureTextView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class ReviewFragment extends Fragment {

    private Review currentReview;
    private Movie currentMovie;

    private String reviewUrl;
    private ReviewActivity activity;

    private ScrollView scrollView;
    private TextView mediaTitleText;
    private TextView authorTextView;
    private GestureTextView reviewTextView;

    public static ReviewFragment newInstance(Review review, Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable("review", review);
        args.putSerializable("movie", movie);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        menu.add(R.string.OpenReviewUrl)
            .setIcon(R.drawable.ic_redo)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                if (reviewUrl != null) {
                    Browser.openUrl(activity, reviewUrl);
                } else {
                    Toast.makeText(getContext(), R.string.ReviewSourceUnavailable, Toast.LENGTH_SHORT).show();
                }

                return true;
            });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (ReviewActivity) getActivity();

        activity.binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.binding.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.binding.toolbarTitle.setText(R.string.ReviewDetails);
        activity.binding.toolbarTitle.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        View fragmentView = inflater.inflate(R.layout.fragment_review, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        scrollView = fragmentView.findViewById(R.id.scroll_view);

        LinearLayout linearLayout = fragmentView.findViewById(R.id.headers_layout);

        FrameLayout mediaTitleLayout = new FrameLayout(activity);
        mediaTitleLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linearLayout.addView(mediaTitleLayout);

        ImageView mediaIcon = new ImageView(activity);
        mediaIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(activity, Theme.primaryTextColor())));
        mediaIcon.setLayoutParams(LayoutHelper.makeFrame(22, 22, Gravity.START | Gravity.TOP, 0, 1, 0, 0));
        mediaTitleLayout.addView(mediaIcon);

        mediaTitleText = new TextView(activity);
        mediaTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mediaTitleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        mediaTitleText.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        mediaTitleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 24 + 6, 0, 0, 0));
        mediaTitleLayout.addView(mediaTitleText);

        FrameLayout authorLayout = new FrameLayout(activity);
        authorLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 0));
        linearLayout.addView(authorLayout);

        ImageView authorIconView = new ImageView(activity);
        authorIconView.setImageDrawable(Theme.getIcon(R.drawable.ic_account, ContextCompat.getColor(activity, Theme.primaryTextColor())));
        authorIconView.setLayoutParams(LayoutHelper.makeFrame(20, 20, Gravity.START | Gravity.TOP, 0, 1, 0, 0));
        authorLayout.addView(authorIconView);

        authorTextView = new TextView(activity);
        authorTextView.setEllipsize(TextUtils.TruncateAt.END);
        authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorTextView.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        authorTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        authorTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 20 + 6, 0, 0, 0));
        authorLayout.addView(authorTextView);

        reviewTextView = fragmentView.findViewById(R.id.review_text);
        reviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        reviewTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        reviewTextView.getController().getSettings().setMaxZoom(2.0F);
        if (AndroidUtilsDev.zoomReview()) {
            reviewTextView.getController().getSettings().enableGestures();
        } else {
            reviewTextView.getController().getSettings().disableGestures();
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentReview = getArguments().getParcelable("review");
            currentMovie = (Movie) getArguments().getSerializable("movie");
            load();
        }
    }

    private void load() {
        mediaTitleText.setText(currentMovie.title + " (" + currentMovie.releaseDate.substring(0, 4) + ")");
        authorTextView.setText(currentReview.author);
        reviewTextView.setText(currentReview.content);
        reviewUrl = currentReview.url;
    }
}