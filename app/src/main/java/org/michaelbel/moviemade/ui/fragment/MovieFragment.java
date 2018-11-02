package org.michaelbel.moviemade.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.snackbar.Snackbar;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.ui.activity.MovieActivity;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.RatingView;
import org.michaelbel.moviemade.ui_old.view.CheckedButton;
import org.michaelbel.moxy.android.MvpAppCompatFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;

import static android.view.View.VISIBLE;

public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, View.OnClickListener {

    //private Movie extraMovie;
    //private Movie loadedMovie;
    //private MovieRealm extraMovieRealm;

    //private ArrayList<Genre> genres = new ArrayList<>();
    //private ArrayList<Trailer> trailers = new ArrayList<>();
    //private ArrayList<Keyword> keywords = new ArrayList<>();

    private MovieActivity activity;

    private View view;

    private ProgressBar progressBar;
    private EmptyView emptyView;

    private ImageView posterImage;

    private LinearLayout shortInfoLayout;

    private RatingView ratingView;
    private TextView ratingText;
    private AppCompatTextView voteCountText;

    private LinearLayout releaseDateLayout;
    private ImageView releaseDateIcon;
    private TextView releaseDateText;

    private LinearLayout runtimeLayout;
    private ImageView runtimeIcon;
    private TextView runtimeText;

    private LinearLayout langLayout;
    private ImageView langIcon;
    private TextView langText;

    private LinearLayout titleLayout;
    private TextView titleText;
    private TextView taglineText;

    private View dividerView;
    private TextView overviewText;

    private LinearLayout buttonsLayout;
    private CheckedButton faveButton;
    private CheckedButton watchButton;

    private FrameLayout trailersLayout;

    @InjectPresenter
    public MoviePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MovieActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyView = view.findViewById(R.id.empty_view);
        posterImage = view.findViewById(R.id.poster_image);
        ratingView = view.findViewById(R.id.rating_view);
        ratingText = view.findViewById(R.id.rating_text);
        voteCountText = view.findViewById(R.id.vote_count_text);
        releaseDateLayout = view.findViewById(R.id.date_layout);
        releaseDateIcon = view.findViewById(R.id.release_date_icon);
        releaseDateText = view.findViewById(R.id.release_date_text);
        runtimeLayout = view.findViewById(R.id.runtime_layout);
        runtimeIcon = view.findViewById(R.id.runtime_icon);
        runtimeText = view.findViewById(R.id.runtime_text);
        langLayout = view.findViewById(R.id.lang_layout);
        langIcon = view.findViewById(R.id.lang_icon);
        langText = view.findViewById(R.id.lang_text);
        titleText = view.findViewById(R.id.title_text);
        taglineText = view.findViewById(R.id.tagline_text);
        dividerView = view.findViewById(R.id.divider_view);
        overviewText = view.findViewById(R.id.overview_text);
        shortInfoLayout = view.findViewById(R.id.info_layout);
        titleLayout = view.findViewById(R.id.title_layout);
        buttonsLayout = view.findViewById(R.id.buttons_layout);
        faveButton = view.findViewById(R.id.fave_button);
        watchButton = view.findViewById(R.id.watch_button);
        trailersLayout = view.findViewById(R.id.trailers_layout);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runtimeText.setText(R.string.loading_status);
        runtimeIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_clock, ContextCompat.getColor(activity, R.color.iconActive)));

        taglineText.setText(R.string.loading_tagline);

        trailersLayout.setOnClickListener(this);

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setPoster(RequestOptions options, String posterPath) {
        Glide.with(activity).asBitmap()
             .load(posterPath)
             .apply(options)
             .into(new BitmapImageViewTarget(posterImage) {
                @Override
                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    super.onResourceReady(bitmap, transition);
                    Palette.from(bitmap).generate(palette -> posterImage.setBackgroundColor(ContextCompat.getColor(activity, R.color.primary)));
                }
            });
        posterImage.setVisibility(VISIBLE);
    }

    @Override
    public void setMovieTitle(String title) {
        titleText.setText(title);
    }

    @Override
    public void setOverview(String overview) {
        if (TextUtils.isEmpty(overview)) {
            overviewText.setText(R.string.no_overview);
            return;
        }

        overviewText.setText(overview);
    }

    @Override
    public void setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingText.setText(String.valueOf(voteAverage));
    }

    @Override
    public void setVoteCount(int voteCount) {
        voteCountText.setText(String.valueOf(voteCount));
    }

    @Override
    public void setReleaseDate(String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) {
            shortInfoLayout.removeView(releaseDateLayout);
            return;
        }

        releaseDateIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_calendar, ContextCompat.getColor(activity, R.color.iconActive)));
        releaseDateText.setText(releaseDate);
    }

    @Override
    public void setOriginalLanguage(String originalLanguage) {
        if (TextUtils.isEmpty(originalLanguage)) {
            shortInfoLayout.removeView(langLayout);
            return;
        }

        langIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_earth, ContextCompat.getColor(activity, R.color.iconActive)));
        langText.setText(originalLanguage);
    }

    @Override
    public void setRuntime(String runtime) {
        if (runtime == null) {
            runtimeText.setText(R.string.unknown);
            return;
        }

        runtimeText.setText(runtime);
    }

    @Override
    public void setTagline(String tagline) {
        if (tagline == null || TextUtils.isEmpty(tagline)) {
            titleLayout.removeView(taglineText);
            return;
        }

        taglineText.setText(tagline);
    }

    @Override
    public void showConnectionError() {
        Snackbar.make(view, R.string.no_connection, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == trailersLayout) {
            activity.startTrailers(activity.movie);
        }
    }

    /*@Override
    public void showMovie(Movie movie) {
        //buttonsLayout.setVisibility(VISIBLE);

        movieView.favoriteButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.watchingButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.setFavoriteButton();
        movieView.setWatchingButton();
        movieView.topLayoutLoaded();
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addCountries(AndroidUtils.formatCountries(movie.countries));
        if (movieView.getCompanies().isEmpty()) {
            movieView.addCompanies(movie.companies);
        }

        movieView.addGenres(movie.genres);

        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);
        movieView.addCollection(movie.belongsToCollection);*//*

        //presenter.loadCredits(movie.id);
        //presenter.loadTrailers(movie.id);
        //presenter.loadImages(movie.id);
        //presenter.loadKeywords(movie.id);

        //genres.clear();
        //genres.addAll(movie.genres);
        //movieView.getGenresView().setClickable(true);
    }

    @Override
    public void onWatchingButtonClick(View view) {
        *//*if (loadedMovie != null) {
            presenter.setMovieWatching(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieWatching(extraMovieRealm);
        }*//*
    }

    @Override
    public void watchingButtonState(boolean state) {
        *//*watchButton.setChecked(state);*//*
    }

    @Override
    public void onTrailerClick(View view, String trailerKey) {
        *//*startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));*//*
    }

    @Override
    public void onTrailersSectionClick(View view) {
        *//*activity.startTrailers(loadedMovie.title, trailers);*//*
    }

    @Override
    public void onGenresSectionClick(View view) {
        *//*activity.startGenres(genres);*//*
    }

    @Override
    public void onMovieUrlClick(View view, int position) {
        *//*if (extraMovieRealm != null) {
            if (position == 1) {
                Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovieRealm.id));
            } else if (position == 2) {
                Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, extraMovieRealm.imdbId));
            } else if (position == 3) {
                Browser.openUrl(activity, extraMovieRealm.homepage);
            }
        } else if (position == 1) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE, extraMovie.id));
        } else if (position == 2) {
            Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, loadedMovie.imdbId));
        } else if (position == 3) {
            Browser.openUrl(activity, loadedMovie.homepage);
        }*//*
    }

    @Override
    public void onGenreClick(View view, Genre genre) {
        *//*activity.startGenre(genre);*//*
    }

    @Override
    public void onPostersClick(View view) {
        *//*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
        }*//*
    }

    @Override
    public void onBackdropsClick(View view) {
        *//*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
        }*//*
    }

    @Override
    public void onKeywordClick(View view, Keyword keyword) {
        *//*activity.startKeyword(keyword);*//*
    }

    @Override
    public void showKeywords(List<Keyword> results) {
        *//*keywords.clear();
        keywords.addAll(results);

        if (movieView.getKeywords().isEmpty()) {
            movieView.addKeywords(keywords);
        }*//*
    }

    @Override
    public void onCollectionClick(View view) {
        *//*activity.startCollection(loadedMovie.belongsToCollection);*//*
    }

    @Override
    public void onCompanyClick(View view, Company company) {
        *//*activity.startCompany(company);*//*
    }

    @Override
    public void onPosterClick(View view) {
        *//*imageAnimator.enterSingle(true);*//*
    }*/
}