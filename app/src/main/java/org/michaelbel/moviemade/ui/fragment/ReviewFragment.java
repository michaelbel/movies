package org.michaelbel.moviemade.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Review;
import org.michaelbel.moviemade.ui.ReviewActivity;
import org.michaelbel.moviemade.ui.view.widget.GestureTextView;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class ReviewFragment extends Fragment {

    private Review review;
    private Movie movie;
    private MovieRealm movieRealm;

    private String url;
    private ReviewActivity activity;

    private ScrollView scrollView;
    private TextView mediaTitle;
    private TextView authorText;
    private GestureTextView reviewText;

    public static ReviewFragment newInstance(Review review, Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable("review", review);
        args.putSerializable("movie", movie);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ReviewFragment newInstance(Review review, MovieRealm movie) {
        Bundle args = new Bundle();
        args.putParcelable("review", review);
        args.putParcelable("movieRealm", movie);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (ReviewActivity) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {
        menu.add(R.string.OpenReviewUrl)
            .setIcon(R.drawable.ic_redo)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                if (url != null) {
                    Browser.openUrl(activity, url);
                } else {
                    Toast.makeText(getContext(), R.string.ReviewSourceUnavailable, Toast.LENGTH_SHORT).show();
                }

                return true;
            });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        activity.authorLayout.setOnClickListener(v -> {
            if (AndroidUtils.scrollToTop()) {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        View fragmentView = inflater.inflate(R.layout.fragment_review, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        scrollView = fragmentView.findViewById(R.id.scroll_view);
        scrollView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());

        FrameLayout mediaTitleLayout = new FrameLayout(activity);
        mediaTitleLayout.setPadding(ScreenUtils.dp(0), ScreenUtils.dp(12), ScreenUtils.dp(16), ScreenUtils.dp(12));
        mediaTitleLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        mediaTitleLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        activity.authorLayout.addView(mediaTitleLayout);

        ImageView mediaIcon = new ImageView(activity);
        mediaIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_movieroll, ContextCompat.getColor(activity, Theme.iconActiveColor())));
        mediaIcon.setLayoutParams(LayoutHelper.makeFrame(22, 22, Gravity.START | Gravity.TOP, 0, 1, 0, 0));
        mediaTitleLayout.addView(mediaIcon);

        mediaTitle = new TextView(activity);
        mediaTitle.setTextIsSelectable(AndroidUtils.textSelect());
        mediaTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        mediaTitle.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        mediaTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        mediaTitle.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 24 + 6, 0, 0, 0));
        mediaTitleLayout.addView(mediaTitle);

        FrameLayout authorLayout = new FrameLayout(activity);
        authorLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.primaryColor()));
        authorLayout.setPadding(ScreenUtils.dp(0), ScreenUtils.dp(0), ScreenUtils.dp(16), ScreenUtils.dp(12));
        authorLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));
        activity.authorLayout.addView(authorLayout);

        ImageView authorIconView = new ImageView(activity);
        authorIconView.setImageDrawable(Theme.getIcon(R.drawable.ic_account_circle, ContextCompat.getColor(activity, Theme.iconActiveColor())));
        authorIconView.setLayoutParams(LayoutHelper.makeFrame(22, 22, Gravity.START | Gravity.TOP, 0, 1, 0, 0));
        authorLayout.addView(authorIconView);

        authorText = new TextView(activity);
        authorText.setTextIsSelectable(AndroidUtils.textSelect());
        authorText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        authorText.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        authorText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        authorText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 24 + 6, 0, 0, 0));
        authorLayout.addView(authorText);

        reviewText = fragmentView.findViewById(R.id.review_text);
        reviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        reviewText.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        reviewText.getController().getSettings().setMaxZoom(2.0F);
        if (AndroidUtils.zoomReview()) {
            reviewText.getController().getSettings().enableGestures();
        } else {
            reviewText.getController().getSettings().disableGestures();
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        review = getArguments().getParcelable("review");
        movie = (Movie) getArguments().getSerializable("movie");
        movieRealm = getArguments().getParcelable("movieRealm");

        load();
    }

    private void load() {
        if (movieRealm != null) {
            mediaTitle.setText(movieRealm.title + " (" + movieRealm.releaseDate + ")");
        } else {
            mediaTitle.setText(movie.title + " (" + movie.releaseDate.substring(0, 4) + ")");
        }

        authorText.setText(review.author);
        reviewText.setText(review.content);
        url = review.url;
    }
}