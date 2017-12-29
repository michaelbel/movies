package org.michaelbel.application.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.ui.ReviewActivity;
import org.michaelbel.application.ui.view.widget.GestureTextView;

@SuppressWarnings("all")
public class ReviewFragment extends Fragment {

    private Review currentReview;
    private Movie currentMovie;

    private String reviewUrl;
    private ReviewActivity activity;

    private ProgressBar progressBar;
    private TextView mediaTitleText;
    private TextView authorTextView;
    private GestureTextView reviewTextView;

    public static ReviewFragment newInstance(Review review, Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("review", review);
        args.putSerializable("movie", movie);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (ReviewActivity) getActivity();
        setHasOptionsMenu(true);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.toolbarTextView.setText(R.string.ReviewDetails);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        NestedScrollView reviewLayout = new NestedScrollView(activity);
        reviewLayout.setScrollbarFadingEnabled(true);
        // todo ScrollBar
        //reviewLayout.setVerticalScrollBarEnabled(true);
        reviewLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        fragmentView.addView(reviewLayout);

        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        reviewLayout.addView(contentLayout);

        LinearLayout mediaLayout = new LinearLayout(activity);
        mediaLayout.setOrientation(LinearLayout.HORIZONTAL);
        mediaLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        contentLayout.addView(mediaLayout);

        ImageView movieIconView = new ImageView(activity);
        movieIconView.setImageDrawable(Theme.getIcon(R.drawable.ic_movie, ContextCompat.getColor(activity, Theme.primaryTextColor())));
        movieIconView.setLayoutParams(LayoutHelper.makeLinear(22, 22, Gravity.START | Gravity.CENTER_VERTICAL));
        mediaLayout.addView(movieIconView);

        mediaTitleText = new TextView(activity);
        mediaTitleText.setLines(1);
        mediaTitleText.setMaxLines(1);
        mediaTitleText.setSingleLine();
        //mediaTitleText.setEllipsize(TextUtils.TruncateAt.END);
        mediaTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mediaTitleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        mediaTitleText.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        mediaTitleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 6, 0, 0, 0));
        mediaLayout.addView(mediaTitleText);

        LinearLayout authorLayout = new LinearLayout(activity);
        authorLayout.setOrientation(LinearLayout.HORIZONTAL);
        authorLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 0));
        contentLayout.addView(authorLayout);

        ImageView authorIconView = new ImageView(activity);
        authorIconView.setImageDrawable(Theme.getIcon(R.drawable.ic_account, ContextCompat.getColor(activity, Theme.primaryTextColor())));
        authorIconView.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START | Gravity.CENTER_VERTICAL));
        authorLayout.addView(authorIconView);

        authorTextView = new TextView(activity);
        authorTextView.setMaxLines(1);
        authorTextView.setEllipsize(TextUtils.TruncateAt.END);
        authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        authorTextView.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        authorTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 6, 0, 0, 0));
        authorLayout.addView(authorTextView);

        reviewTextView = new GestureTextView(activity);
        reviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        reviewTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        reviewTextView.getController().getSettings().setMaxZoom(2.0F);
        reviewTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 16));
        contentLayout.addView(reviewTextView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentReview = (Review) getArguments().getSerializable("review");
            currentMovie = (Movie) getArguments().getSerializable("movie");
            load();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
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

    private void load() {
        String mediaTitle = currentMovie.title + " (" + currentMovie.releaseDate.substring(0, 4) + ")";

        mediaTitleText.setText(mediaTitle);
        authorTextView.setText(currentReview.author);
        reviewTextView.setText(currentReview.content);
        reviewUrl = currentReview.url;

        onLoadSuccessful();
    }

    /*private void loadReview(String reviewId) {
        REVIEWS service = ApiFactory.getRetrofit().create(REVIEWS.class);
        Call<Review> call = service.getDetails(reviewId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Review review = response.body();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                onLoadError();
            }
        });
    }*/

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
    }
}