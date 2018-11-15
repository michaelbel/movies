package org.michaelbel.moviemade.ui.modules.movie;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.views.appbar.AppBarState;
import org.michaelbel.moviemade.ui.modules.main.views.appbar.AppBarStateChangeListener;
import org.michaelbel.moviemade.ui.modules.movie.views.BackdropView;
import org.michaelbel.moviemade.utils.DeviceUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("all")
public class MovieActivity extends BaseActivity {

    public static final String KEY_MOVIE = "movie";

    public Movie movie;
    public ViewsTransitionAnimator imageAnimator;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.app_bar) AppBarLayout appBar;
    @BindView(R.id.toolbar_title) TextView toolbarTitle;
    @BindView(R.id.backdrop_image) BackdropView backdropImage;
    @BindView(R.id.collapsing_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.demo_full_background) View fullBackground;
    @BindView(R.id.demo_pager) ViewPager viewPager;
    @BindView(R.id.demo_pager_title) AppCompatTextView titleText;
    @BindView(R.id.full_image) GestureImageView fullImage;
    @BindView(R.id.full_image_toolbar) Toolbar fullImageToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(KEY_MOVIE);

        MovieFragment fragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.presenter.setMovieDetailsFromExtra(movie);
        fragment.presenter.loadMovieDetails(movie.getId());

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
        backdropImage.setImage(movie.getBackdropPath());

        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary));
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fullImageToolbar.getLayoutParams();
        params.topMargin = DeviceUtil.INSTANCE.getStatusBarHeight(this);

        fullImageToolbar.setNavigationOnClickListener(view -> onBackPressed());

        fullImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullImageToolbar.setVisibility(isSystemStatusBarShown() ? View.INVISIBLE : View.VISIBLE);
                showSystemStatusBar(!isSystemStatusBarShown());
            }
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

    public void showSystemStatusBar(boolean state) {
        // If Version SDK >= KITKAT
        int flags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE;
        getWindow().getDecorView().setSystemUiVisibility(state ? 0 : flags);
    }

    public boolean isSystemStatusBarShown() {
        return (getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0;
    }
}