package org.michaelbel.moviemade.ui.modules.movie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.appbar.AppBarState;
import org.michaelbel.moviemade.ui.modules.main.appbar.AppBarStateChangeListener;
import org.michaelbel.moviemade.utils.SharedPrefsKt;
import org.michaelbel.moviemade.utils.TmdbConfigKt;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.Locale;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import butterknife.Unbinder;

@SuppressWarnings("all")
public class MovieActivity extends BaseActivity {

    public Movie movie;
    private Unbinder unbinder;
    public ViewsTransitionAnimator imageAnimator;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.app_bar) AppBarLayout appBar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.backdrop_image) AppCompatImageView backdropImage;
    @BindView(R.id.collapsing_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.demo_full_background) View fullBackground;
    @BindView(R.id.demo_pager) ViewPager viewPager;
    @BindView(R.id.demo_pager_title) AppCompatTextView titleText;
    @BindView(R.id.full_image) GestureImageView fullImage;
    @BindView(R.id.full_image_toolbar) Toolbar fullImageToolbar;

    @Inject SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        unbinder = ButterKnife.bind(this);
        Moviemade.getComponent().injest(this);

        movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);

        MovieFragment fragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.setMovieDetailsFromExtra(movie);
            fragment.presenter.loadDetails(movie.getId());
        }

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.transparent40));

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitle(null);

        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarState state) {
                if (state == AppBarState.COLLAPSED) {
                    toolbarTitle.setText(movie.getTitle());
                } else {
                    toolbarTitle.setText(null);
                }
            }
        });

        toolbarTitle.setText(movie.getTitle());
        Glide.with(this).load(String.format(Locale.US, TmdbConfigKt.TMDB_IMAGE, "original", movie.getBackdropPath())).thumbnail(0.1F).into(backdropImage);

        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary));
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fullImageToolbar.getLayoutParams();
        params.topMargin = DeviceUtil.INSTANCE.getStatusBarHeight(this);

        fullImageToolbar.setNavigationOnClickListener(view -> onBackPressed());

        fullImage.setOnClickListener(v -> {
            fullImageToolbar.setVisibility(isSystemStatusBarShown() ? View.INVISIBLE : View.VISIBLE);
            showSystemStatusBar(!isSystemStatusBarShown());
        });
    }

    @Override
    public void onBackPressed() {
        if (imageAnimator != null && !imageAnimator.isLeaving()) {
            imageAnimator.exit(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnLongClick(R.id.backdrop_image)
    public boolean backdropLongClick(View v) {
        if (!sharedPreferences.getString(SharedPrefsKt.KEY_SESSION_ID, "").isEmpty()) {
            DeviceUtil.INSTANCE.vibrate(this, 15);
            BackdropDialog dialog = BackdropDialog.newInstance(String.format(Locale.US, TmdbConfigKt.TMDB_IMAGE, "original", movie.getBackdropPath()));
            dialog.show(getSupportFragmentManager(), "tag");
            return true;
        }

        return false;
    }

    public void showSystemStatusBar(boolean state) {
        // If Version SDK >= KITKAT.
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().getDecorView().setSystemUiVisibility(state ? 0 : flags);
    }

    public boolean isSystemStatusBarShown() {
        return (getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0;
    }
}