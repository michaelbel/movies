package org.michaelbel.moviemade.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Url;
import org.michaelbel.moviemade.browser.Browser;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.ui.fragment.MovieFragment;
import org.michaelbel.moviemade.ui.view.BackdropView;
import org.michaelbel.moviemade.ui.view.appbar.AppBarState;
import org.michaelbel.moviemade.ui.view.appbar.AppBarStateChangeListener;

import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

@SuppressWarnings("all")
public class MovieActivity extends BaseActivity {

    public Movie movie;

    private Context context;

    private Menu actionMenu;
    private MenuItem menu_share;
    private MenuItem menu_tmdb;
    private MenuItem menu_imdb;

    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        context = MovieActivity.this;

        // Создать новый экземпляр класса Movie и присвоить ему все доступные передаваемые значения.
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
        movie.genreIds = getIntent().getIntegerArrayListExtra("genreIds");
        movie.originalTitle = getIntent().getStringExtra("originalTitle");
        movie.originalLanguage = getIntent().getStringExtra("originalLanguage");
        movie.popularity = getIntent().getDoubleExtra("popularity", 0);

        MovieFragment fragment = (MovieFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.presenter.setMovieDetailsFromExtra(movie);
        fragment.presenter.loadMovieDetails(movie.id);

        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.primary));
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.transparent20));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        toolbar.setTitle(null);

        AppBarLayout appBar = findViewById(R.id.app_bar);
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

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(movie.title);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_layout);
        collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(context, R.color.primary));
        collapsingToolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(context, android.R.color.transparent));

        BackdropView backdropImage = findViewById(R.id.backdrop_image);
        backdropImage.setImage(movie.backdropPath);
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
            Browser.openUrl(context, String.format(Locale.US, Url.TMDB_MOVIE, movie.id));
        } /*else if (item == menu_imdb) {
            Browser.openUrl(context, String.format(Locale.US, Url.IMDB_MOVIE, movie.imdbId));
        }*/

        return true;
    }
}