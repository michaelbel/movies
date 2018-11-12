package org.michaelbel.moviemade.ui.modules.movie;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.views.GestureImageView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.browser.Browser;
import org.michaelbel.moviemade.extensions.DeviceUtil;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.views.appbar.AppBarState;
import org.michaelbel.moviemade.ui.modules.main.views.appbar.AppBarStateChangeListener;
import org.michaelbel.moviemade.ui.modules.movie.views.BackdropView;
import org.michaelbel.tmdb.v3.json.Movie;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("all")
public class MovieActivity extends BaseActivity {

    public Movie movie;

    private Menu actionMenu;
    private MenuItem menu_share;
    private MenuItem menu_tmdb;
    private MenuItem menu_imdb;

    public ViewsTransitionAnimator imageAnimator;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.app_bar)
    public AppBarLayout appBar;

    @BindView(R.id.toolbar_title)
    public TextView toolbarTitle;

    @BindView(R.id.backdrop_image)
    public BackdropView backdropImage;

    @BindView(R.id.collapsing_layout)
    public CollapsingToolbarLayout collapsingToolbarLayout;



    @BindView(R.id.demo_full_background)
    public View fullBackground;

    @BindView(R.id.demo_pager)
    public ViewPager viewPager;

    @BindView(R.id.demo_pager_title)
    public AppCompatTextView titleText;

    @BindView(R.id.full_image)
    public GestureImageView fullImage;

    @BindView(R.id.full_image_toolbar)
    public Toolbar fullImageToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        movie = new Movie();
        movie.id =  getIntent().getIntExtra("id", 0);
        movie.title = getIntent().getStringExtra("title");
        movie.backdropPath = getIntent().getStringExtra("backdropPath");
        movie.posterPath = getIntent().getStringExtra("posterPath");
        movie.overview = getIntent().getStringExtra("overview");
        movie.voteAverage = getIntent().getFloatExtra("voteAverage", 0);
        movie.voteCount = getIntent().getIntExtra("voteCount", 0);
        movie.adult = getIntent().getBooleanExtra("adult", false);
        movie.video = getIntent().getBooleanExtra("video", false);
        movie.releaseDate = getIntent().getStringExtra("releaseDate");
        //movie.genreIds = getIntent().getIntegerArrayListExtra("genreIds");
        movie.originalTitle = getIntent().getStringExtra("originalTitle");
        movie.originalLanguage = getIntent().getStringExtra("originalLanguage");
        movie.popularity = getIntent().getDoubleExtra("popularity", 0);

        MovieFragment fragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.presenter.setMovieDetailsFromExtra(movie);
        fragment.presenter.loadMovieDetails(movie.id);

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
                    toolbarTitle.setText(movie.title);
                } else {
                    toolbarTitle.setText(null);
                }
            }
        });

        toolbarTitle.setText(movie.title);
        backdropImage.setImage(movie.backdropPath);

        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.primary));
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, android.R.color.transparent));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fullImageToolbar.getLayoutParams();
        params.topMargin = DeviceUtil.getStatusBarHeight(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        actionMenu = menu;
        menu_share = menu.add(R.string.Share).setIcon(R.drawable.ic_anim_share).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        menu_tmdb = menu.add(R.string.ViewOnTMDb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        //menu_imdb = menu.add(R.string.ViewOnIMDb).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER);
        return super.onCreateOptionsMenu(menu);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == menu_share) {
            Drawable icon = actionMenu.getItem(0).getIcon();
            if (icon instanceof Animatable) {
                ((Animatable) icon).start();
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, String.format(Locale.US, Url.TMDB_MOVIE, movie.id));
            startActivity(Intent.createChooser(intent, getString(R.string.ShareVia)));
        } else if (item == menu_tmdb) {
            Browser.openUrl(this, String.format(Locale.US, Url.TMDB_MOVIE, movie.id));
        } /*else if (item == menu_imdb) {
            Browser.openUrl(context, String.format(Locale.US, Url.IMDB_MOVIE, movie.imdbId));
        }*/

        return true;
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