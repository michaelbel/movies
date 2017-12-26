package org.michaelbel.application.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.rest.api.REVIEWS;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.ui.ReviewActivity;
import org.michaelbel.application.ui.view.EmptyView;
import org.michaelbel.application.ui.view.widget.GestureTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDetailsFragment extends Fragment {

    private String reviewId;
    private String reviewUrl;
    private String reviewAuthor;
    private String mediaTitle;

    private ReviewActivity activity;

    private TextView mediaTitleText;
    private TextView authorTextView;
    private GestureTextView reviewTextView;
    private EmptyView emptyView;
    private ProgressBar progressBar;

    public static ReviewDetailsFragment newInstance(String reviewId) {
        Bundle args = new Bundle();
        args.putString("reviewId", reviewId);

        ReviewDetailsFragment fragment = new ReviewDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (ReviewActivity) getActivity();

        View fragmentView = inflater.inflate(R.layout.fragment_review_details, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        setHasOptionsMenu(true);

        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.toolbar.setNavigationOnClickListener(view -> activity.finish());
        activity.toolbarTextView.setText("Review details");

        emptyView = fragmentView.findViewById(R.id.empty_view);
        emptyView.setImage(R.drawable.review_placeholder);
        emptyView.setVisibility(View.INVISIBLE);

        progressBar = fragmentView.findViewById(R.id.progress_bar);

        mediaTitleText = fragmentView.findViewById(R.id.media_title);
        mediaTitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        mediaTitleText.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        mediaTitleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        authorTextView = fragmentView.findViewById(R.id.review_author);
        authorTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorTextView.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        authorTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        reviewTextView = fragmentView.findViewById(R.id.review_text);
        reviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        reviewTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        reviewTextView.getController().getSettings().setMaxZoom(2.0F);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            reviewId = getArguments().getString("reviewId");
            loadReview();
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        menu.add(R.string.OpenReviewUrl)
                .setIcon(R.drawable.ic_url)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                .setOnMenuItemClickListener(menuItem -> {
                    if (reviewUrl != null) {
                        Browser.openUrl(activity, reviewUrl);
                    } else {
                        Toast.makeText(getContext(), "Review url is not accessed", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                });
    }

    private void loadReview() {
        REVIEWS service = ApiFactory.getRetrofit().create(REVIEWS.class);
        Call<Review> call = service.getDetails(reviewId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Review review = response.body();

                    if (review != null) {
                        mediaTitleText.setText(review.mediaTitle);
                        authorTextView.setText("Author: " + review.author);
                        reviewTextView.setText(review.content);
                        reviewUrl = review.url;

                        onLoadSuccessful();
                    } else {
                        onLoadError();
                    }
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                //FirebaseCrash.report(t);
                onLoadError();
            }
        });
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
    }
}