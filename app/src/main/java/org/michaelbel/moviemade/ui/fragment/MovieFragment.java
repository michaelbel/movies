package org.michaelbel.moviemade.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.palette.graphics.Palette;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.mvp.presenter.MoviePresenter;
import org.michaelbel.moviemade.mvp.view.MvpMovieView;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Movie;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui_old.interfaces.MovieViewListener;
import org.michaelbel.moviemade.ui_old.view.CheckedButton;
import org.michaelbel.moviemade.ui_old.view.EmptyView;
import org.michaelbel.moviemade.ui_old.view.RatingView;
import org.michaelbel.moviemade.ui.activity.MovieActivity;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.List;
import java.util.Locale;

import static android.view.View.VISIBLE;

public class MovieFragment extends MvpAppCompatFragment implements MvpMovieView, MovieViewListener {

    //private Movie extraMovie;
    //private Movie loadedMovie;
    //private MovieRealm extraMovieRealm;

    //private ArrayList<Genre> genres = new ArrayList<>();
    //private ArrayList<Trailer> trailers = new ArrayList<>();
    //private ArrayList<Keyword> keywords = new ArrayList<>();

    private MovieActivity activity;

    private ProgressBar progressBar;
    private EmptyView emptyView;

    private ImageView posterImage;

    private LinearLayout shortInfoLayout;

    private RatingView ratingView;
    private TextView ratingText;
    private TextView voteCountText;

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
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
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
        shortInfoLayout = view.findViewById(R.id.short_info_layout);
        titleLayout = view.findViewById(R.id.title_layout);
        buttonsLayout = view.findViewById(R.id.buttons_layout);
        faveButton = view.findViewById(R.id.fave_button);
        watchButton = view.findViewById(R.id.watch_button);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView.setOnClickListener(v -> presenter.loadMovie(presenter.movie));

        //faveButton.setStyle(CheckedButton.FAVORITE);
        //faveButton.setOnClickListener(v -> presenter.setMovieFavorite(extraMovie));

        //watchButton.setStyle(CheckedButton.WATCHING);
        //watchButton.setOnClickListener(v -> presenter.setMovieWatching(extraMovie));
    }

    @Override
    public void showMovie(Movie movie) {
        setPoster(movie.posterPath);
        ratingView.setRating(movie.voteAverage);
        ratingText.setText(String.valueOf(movie.voteAverage));
        voteCountText.setText(String.valueOf(movie.voteCount));

        if (TextUtils.isEmpty(movie.releaseDate)) {
            shortInfoLayout.removeView(releaseDateLayout);
        } else {
            releaseDateIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_calendar, ContextCompat.getColor(activity, R.color.iconActive)));
            releaseDateText.setText(AndroidExtensions.formatReleaseDate(movie.releaseDate));
        }

        runtimeIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_clock, ContextCompat.getColor(activity, R.color.iconActive)));
        runtimeText.setText(movie.runtime != 0 ? AndroidExtensions.formatRuntime(movie.runtime) : null);

        if (TextUtils.isEmpty(movie.originalLanguage)) {
            shortInfoLayout.removeView(langLayout);
            langLayout.setVisibility(View.GONE);
        } else {
            langIcon.setImageDrawable(Extensions.getIcon(activity, R.drawable.ic_earth, ContextCompat.getColor(activity, R.color.iconActive)));
            langText.setText(AndroidUtils.formatOriginalLanguage(movie.originalLanguage));
        }

        titleText.setText(movie.title);

        if (TextUtils.isEmpty(movie.tagline)) {
            titleLayout.removeView(taglineText);
        } else {
            taglineText.setText(movie.tagline);
        }

        if (TextUtils.isEmpty(movie.overview)) {
            overviewText.setText(R.string.no_overview);
        } else {
            overviewText.setText(movie.overview);
        }

        //faveButton.setChecked(presenter.isMovieFavorite(movie.id));
        //watchButton.setChecked(presenter.isMovieWatching(movie.id));

        shortInfoLayout.setVisibility(VISIBLE);
        titleLayout.setVisibility(VISIBLE);
        overviewText.setVisibility(VISIBLE);
        dividerView.setVisibility(VISIBLE);

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        //buttonsLayout.setVisibility(VISIBLE);

        /*
        movieView.favoriteButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.watchingButtonVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
        movieView.setFavoriteButton();
        movieView.setWatchingButton();
        movieView.topLayoutLoaded();

        movieView.addOriginalTitle(movie.originalTitle); // from extra
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
        movieView.addCollection(movie.belongsToCollection);*/

        //presenter.loadCredits(movie.id);
        //presenter.loadTrailers(movie.id);
        //presenter.loadImages(movie.id);
        //presenter.loadKeywords(movie.id);

        //genres.clear();
        //genres.addAll(movie.genres);
        //movieView.getGenresView().setClickable(true);
    }

    public void setPoster(@NonNull String posterPath) {
        RequestOptions options = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).priority(Priority.HIGH);
        Glide.with(activity).asBitmap()
             .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath)).apply(options)
             .into(new BitmapImageViewTarget(posterImage) {
                 @Override
                 public void onResourceReady(Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                     super.onResourceReady(bitmap, transition);
                     Palette.from(bitmap).generate(palette -> posterImage.setBackgroundColor(ContextCompat.getColor(activity, R.color.primary)));
                 }
             });
        posterImage.setVisibility(VISIBLE);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(VISIBLE);
        emptyView.setMode(EmptyViewMode.MODE_NO_CONNECTION);
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void showMovieRealm(MovieRealm movie) {
        /*movieView.addPoster(movie.posterPath);
        movieView.addTitle(movie.title);
        movieView.addOverview(movie.overview);
        movieView.addReleaseDate(movie.releaseDate);
        movieView.addVoteAverage(movie.voteAverage);
        movieView.addVoteCount(movie.voteCount);
        movieView.addOriginalTitle(movie.originalTitle);
        movieView.addOriginalLanguage(movie.originalLanguage);
        movieView.addImages(null, null, 0, 0);

        movieView.addTagline(movie.tagline);
        movieView.addRuntime(movie.runtime);
        movieView.addStatus(movie.status);
        movieView.addBudget(movie.budget);
        movieView.addRevenue(movie.revenue);
        movieView.addImdbpage(movie.imdbId);
        movieView.addHomepage(movie.homepage);

        movieView.addCompanies(null);
        movieView.addCountries(null);
        movieView.addGenres(null);
        //movieView.addCollection();

        movieView.favoriteButtonVisibility(View.VISIBLE);
        movieView.watchingButtonVisibility(View.VISIBLE);

        movieView.setFavoriteButton(movie.favorite);
        movieView.setWatchingButton(movie.watching);

        movieView.addTrailers(null);
        movieView.setCrew(null);*/
    }

    @Override
    public void showTrailers(List<Trailer> results) {
        /*trailers.clear();
        trailers.addAll(results);
        movieView.addTrailers(trailers);
        movieView.getTrailersView().setClickable(true);*/
    }

    @Override
    public void showImages(List<Poster> posters, List<Backdrop> backdrops, int postersCount, int backdropsCount) {
        //movieView.addImages(posters, backdrops, postersCount, backdropsCount);
        /*movieView.getImagesView().getPostersAdapter().getView(movieView.getImagesView().getPosterViewPager().getCurrentItem()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2580", "Posters Click");
                if (extraMovie != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
                } else if (extraMovieRealm != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
                }
            }
        });
        movieView.getImagesView().getBackdropsAdapter().getView(movieView.getImagesView().getBackdropViewPager().getCurrentItem()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("2580", "Backdrops Click");
                if (extraMovie != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
                } else if (extraMovieRealm != null) {
                    Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
                }
            }
        });*/
    }

    @Override
    public void showCrew(List<Crew> crews) {
        //movieView.setCrew(crews);
    }

    @Override
    public void realmAdded() {}

    @Override
    public void onFavoriteButtonClick(View view) {
        /*if (loadedMovie != null) {
            presenter.setMovieFavorite(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieFavorite(extraMovieRealm);
        }*/
    }

    @Override
    public void favoriteButtonState(boolean state) {
        /*faveButton.setChecked(state);*/
    }

    @Override
    public void onWatchingButtonClick(View view) {
        /*if (loadedMovie != null) {
            presenter.setMovieWatching(loadedMovie);
        } else if (extraMovieRealm != null) {
            presenter.setMovieWatching(extraMovieRealm);
        }*/
    }

    @Override
    public void watchingButtonState(boolean state) {
        /*watchButton.setChecked(state);*/
    }

    @Override
    public void onTrailerClick(View view, String trailerKey) {
        /*startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));*/
    }

    @Override
    public void onTrailersSectionClick(View view) {
        /*activity.startTrailers(loadedMovie.title, trailers);*/
    }

    @Override
    public void onGenresSectionClick(View view) {
        /*activity.startGenres(genres);*/
    }

    @Override
    public void onMovieUrlClick(View view, int position) {
        /*if (extraMovieRealm != null) {
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
        }*/
    }

    @Override
    public void onGenreClick(View view, Genre genre) {
        /*activity.startGenre(genre);*/
    }

    @Override
    public void onPostersClick(View view) {
        /*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_POSTERS, extraMovieRealm.id));
        }*/
    }

    @Override
    public void onBackdropsClick(View view) {
        /*if (extraMovie != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovie.id));
        } else if (extraMovieRealm != null) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_MOVIE_BACKDROPS, extraMovieRealm.id));
        }*/
    }

    @Override
    public void onKeywordClick(View view, Keyword keyword) {
        /*activity.startKeyword(keyword);*/
    }

    @Override
    public void showKeywords(List<Keyword> results) {
        /*keywords.clear();
        keywords.addAll(results);

        if (movieView.getKeywords().isEmpty()) {
            movieView.addKeywords(keywords);
        }*/
    }

    @Override
    public void onCollectionClick(View view) {
        /*activity.startCollection(loadedMovie.belongsToCollection);*/
    }

    @Override
    public void onCompanyClick(View view, Company company) {
        /*activity.startCompany(company);*/
    }

    @Override
    public void onPosterClick(View view) {
        /*imageAnimator.enterSingle(true);*/
    }
}