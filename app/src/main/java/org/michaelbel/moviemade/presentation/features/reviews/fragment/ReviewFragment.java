package org.michaelbel.moviemade.presentation.features.reviews.fragment;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.presentation.App;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.core.entity.Review;
import org.michaelbel.moviemade.presentation.base.BaseFragment;
import org.michaelbel.moviemade.presentation.features.reviews.GestureTextView;
import org.michaelbel.moviemade.presentation.features.reviews.activity.ReviewActivity;
import org.michaelbel.moviemade.core.utils.AndroidUtil;
import org.michaelbel.moviemade.core.utils.Browser;
import org.michaelbel.moviemade.core.utils.IntentsKt;
import org.michaelbel.moviemade.core.utils.SharedPrefsKt;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import butterknife.BindColor;
import butterknife.BindView;

public class ReviewFragment extends BaseFragment {

    private static final int THEME_LIGHT = 0;
    private static final int THEME_SEPIA = 1;
    private static final int THEME_NIGHT = 2;

    private Review review;
    private ReviewActivity activity;

    @Inject
    SharedPreferences preferences;

    @BindColor(R.color.textColorPrimary) int backgroundLight;
    @BindColor(R.color.sepiaBackground) int backgroundSepia;
    @BindColor(R.color.backgroundColor) int backgroundNight;

    @BindColor(R.color.md_black) int textLight;
    @BindColor(R.color.md_black) int textSepia;
    @BindColor(R.color.nightText) int textNight;

    @BindView(R.id.review_text) GestureTextView reviewText;
    @BindView(R.id.scroll_layout) NestedScrollView scrollLayout;

    public static ReviewFragment newInstance(Review review) {
        Bundle args = new Bundle();
        args.putSerializable(IntentsKt.REVIEW, review);

        ReviewFragment fragment = new ReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        activity = (ReviewActivity) getActivity();
        if (activity != null) {
            ((App) activity.getApplication()).createFragmentComponent().inject(this);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_review, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.item_url) {
            Browser.INSTANCE.openUrl(activity, review.getUrl());
        } else if (item.getItemId() == R.id.item_light) {
            int current = preferences.getInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_NIGHT);
            preferences.edit().putInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_LIGHT).apply();
            changeTheme(current, THEME_LIGHT);
        } else if (item.getItemId() == R.id.item_sepia) {
            int current = preferences.getInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_NIGHT);
            preferences.edit().putInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_SEPIA).apply();
            changeTheme(current, THEME_SEPIA);
        } else if (item.getItemId() == R.id.item_night) {
            int current = preferences.getInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_NIGHT);
            preferences.edit().putInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_NIGHT).apply();
            changeTheme(current, THEME_NIGHT);
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.getToolbar().setOnClickListener(v -> scrollLayout.fullScroll(View.FOCUS_UP));
        activity.getToolbar().setOnLongClickListener(v -> changePinning());

        reviewText.getController().getSettings().enableGestures();
        review = getArguments() != null ? (Review) getArguments().getSerializable(IntentsKt.REVIEW) : null;
        reviewText.setText(review != null ? review.getContent() : "");

        switch (preferences.getInt(SharedPrefsKt.KEY_REVIEW_THEME, THEME_NIGHT)) {
            case THEME_LIGHT:
                scrollLayout.setBackgroundColor(backgroundLight);
                reviewText.setTextColor(textLight);
                break;
            case THEME_SEPIA:
                scrollLayout.setBackgroundColor(backgroundSepia);
                reviewText.setTextColor(textSepia);
                break;
            case THEME_NIGHT:
                scrollLayout.setBackgroundColor(backgroundNight);
                reviewText.setTextColor(textNight);
                break;
            default:
                scrollLayout.setBackgroundColor(backgroundNight);
                reviewText.setTextColor(textNight);
        }
    }

    private void changeTheme(int oldTheme, int newTheme) {
        int backgroundColorStart = 0;
        int backgroundColorEnd = 0;
        int textColorStart = 0;
        int textColorEnd = 0;
        ArgbEvaluator evaluator = new ArgbEvaluator();
        ObjectAnimator backgroundAnim;
        ObjectAnimator textAnim;
        AnimatorSet animatorSet;

        if (oldTheme == THEME_NIGHT && newTheme == THEME_SEPIA) {
            backgroundColorStart = backgroundNight;
            backgroundColorEnd = backgroundSepia;
            textColorStart = textNight;
            textColorEnd = textSepia;
        } else if (oldTheme == THEME_NIGHT && newTheme == THEME_LIGHT) {
            backgroundColorStart = backgroundNight;
            backgroundColorEnd = backgroundLight;
            textColorStart = textNight;
            textColorEnd = textLight;
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_LIGHT) {
            backgroundColorStart = backgroundSepia;
            backgroundColorEnd = backgroundLight;
            textColorStart = textSepia;
            textColorEnd = textLight;
        } else if (oldTheme == THEME_SEPIA && newTheme == THEME_NIGHT) {
            backgroundColorStart = backgroundSepia;
            backgroundColorEnd = backgroundNight;
            textColorStart = textSepia;
            textColorEnd = textNight;
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_SEPIA) {
            backgroundColorStart = backgroundLight;
            backgroundColorEnd = backgroundSepia;
            textColorStart = textLight;
            textColorEnd = textSepia;
        } else if (oldTheme == THEME_LIGHT && newTheme == THEME_NIGHT) {
            backgroundColorStart = backgroundLight;
            backgroundColorEnd = backgroundNight;
            textColorStart = textLight;
            textColorEnd = textNight;
        }

        backgroundAnim = ObjectAnimator.ofObject(scrollLayout, "backgroundColor", evaluator, 0, 0);
        backgroundAnim.setObjectValues(backgroundColorStart, backgroundColorEnd);

        textAnim = ObjectAnimator.ofObject(reviewText, "textColor", evaluator, 0, 0);
        textAnim.setObjectValues(textColorStart, textColorEnd);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(backgroundAnim, textAnim);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new DecelerateInterpolator(2));
        AndroidUtil.INSTANCE.runOnUIThread(animatorSet:: start, 0);
    }

    private boolean changePinning() {
        boolean pin = preferences.getBoolean(SharedPrefsKt.KEY_TOOLBAR_PINNED, false);
        preferences.edit().putBoolean(SharedPrefsKt.KEY_TOOLBAR_PINNED, !pin).apply();
        activity.changePinning(!pin);
        Toast.makeText(activity, !pin ? R.string.msg_toolbar_pinned : R.string.msg_toolbar_unpinned, Toast.LENGTH_SHORT).show();
        return true;
    }
}