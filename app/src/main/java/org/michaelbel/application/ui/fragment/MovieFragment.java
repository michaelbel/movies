package org.michaelbel.application.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.moviemade.browser.Browser;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Backdrop;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Poster;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.rest.response.CreditResponse;
import org.michaelbel.application.rest.response.ImageResponse;
import org.michaelbel.application.rest.response.VideoResponse;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.view.FavButton;
import org.michaelbel.application.ui.view.MovieInfoLayout2;
import org.michaelbel.application.ui.view.MovieInfoLayout1;
import org.michaelbel.application.util.AndroidUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class MovieFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 123;

    private Movie currentMovie;
    private Movie loadedMovie;
    private MovieActivity activity;

    private ArrayList<Trailer> trailersList = new ArrayList<>();

    private TextView emptyView;
    private ProgressBar progressBar;
    private MovieInfoLayout2 movieView;
    private MovieInfoLayout1 movieView2;

    public static MovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putSerializable("movie", movie);

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (MovieActivity) getActivity();
        setHasOptionsMenu(true);

        NestedScrollView fragmentView = new NestedScrollView(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        fragmentView.addView(frameLayout);

        emptyView = new TextView(activity);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setText(R.string.NoConnection);
        emptyView.setVisibility(View.INVISIBLE);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        frameLayout.addView(emptyView);

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM, 0, 0, 0, 36));
        frameLayout.addView(progressBar);

        LinearLayout movieLayout = new LinearLayout(activity);
        movieLayout.setOrientation(LinearLayout.VERTICAL);
        movieLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        frameLayout.addView(movieLayout);

        movieView2 = new MovieInfoLayout1(activity);
        movieView2.setListener(new MovieInfoLayout1.InfoMovieListener() {
            @Override
            public boolean onOverviewLongClick(View view) {
                AndroidUtils.addToClipboard("Overview", currentMovie.overview);
                Toast.makeText(activity, getString(R.string.ClipboardCopied, getString(R.string.Overview)), Toast.LENGTH_SHORT).show();

                if (AndroidUtils.isPermissionGranted(Manifest.permission_group.STORAGE)) {
                    AndroidUtils.createCacheDirectory();
                } else {
                    AndroidUtils.requestPermission(Manifest.permission_group.STORAGE, activity, PERMISSION_REQUEST_CODE);
                }

                return true;
            }

            @Override
            public void onFavButtonClick(View view) {
                /*DatabaseHelper database = DatabaseHelper.getInstance(activity);
                boolean isExist = database.isMovieExist(currentMovie.id);

                if (isExist) {
                    database.removeMovie(currentMovie.id);
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(R.drawable.ic_heart_outline);
                        ((FavButton) view).setText(R.string.Add);
                    }
                } else {
                    database.addMovie(currentMovie);
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(R.drawable.ic_heart);
                        ((FavButton) view).setText(R.string.Remove);
                    }
                }*/

                Realm realm = Realm.getDefaultInstance();
                Movie movieRealm = realm.where(Movie.class).equalTo("id", currentMovie.id).findFirst();
                if (movieRealm == null) {
                    realm.beginTransaction();
                    Movie mr = realm.createObject(Movie.class);
                    mr.favorite = !mr.favorite;
                    realm.commitTransaction();

                    Movie mr2 = realm.where(Movie.class).equalTo("id", currentMovie.id).findFirst();
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(mr2.favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
                        ((FavButton) view).setText(mr2.favorite ? R.string.Remove : R.string.Add);
                    }
                } else {
                    realm.beginTransaction();
                    movieRealm.favorite = !movieRealm.favorite;
                    realm.commitTransaction();

                    Movie movieRealm3 = realm.where(Movie.class).equalTo("id", currentMovie.id).findFirst();
                    if (view instanceof FavButton) {
                        ((FavButton) view).setIcon(movieRealm3.favorite ? R.drawable.ic_heart : R.drawable.ic_heart_outline);
                        ((FavButton) view).setText(movieRealm3.favorite ? R.string.Remove : R.string.Add);
                    }
                }


            }
        });
        movieView2.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        movieLayout.addView(movieView2);




        movieView = new MovieInfoLayout2(getContext());
        movieView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        movieLayout.addView(movieView);

        movieView.setListener(new MovieInfoLayout2.InfoMovieListener() {
            @Override
            public void onTrailersSectionClick(View view) {
                activity.startTrailers(currentMovie, trailersList);
            }

            @Override
            public void onTrailerClick(View view, String trailerKey) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerKey)));
            }

            @Override
            public boolean onTrailerLongClick(View view, String trailerKey) {
                return true;
            }

            @Override
            public void onMovieUrlClick(View view, int position) {
                if (position == 1) {
                    Browser.openUrl(activity, Url.TMDB_MOVIE + currentMovie.id);
                } else if (position == 2) {
                    Browser.openUrl(activity, Url.IMDB_MOVIE + currentMovie.imdbId);
                } else if (position == 3) {
                    Browser.openUrl(activity, currentMovie.homepage);
                }
            }

        });
        return fragmentView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            //createDir();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentMovie = (Movie) getArguments().getSerializable("movie");
        }

        load();
    }

    private void loadMovieDetails(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<Movie> call = service.getDetails(movieId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    loadedMovie = response.body();
                    displayMovie();
                } else {
                    // todo Error.
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // todo Error.
            }
        });
    }

    private void displayMovie() {
        movieView2.setRuntime(loadedMovie.runtime);
        movieView2.setCountries(loadedMovie.countriesList);
        movieView2.setTagline(loadedMovie.tagline);

        movieView.setGenres(loadedMovie.genresList); // Improve with genres ids
        movieView.setStatus(loadedMovie.status);
        movieView.setBudget(loadedMovie.budget);
        movieView.setRevenue(loadedMovie.revenue);
        movieView.setCompanies(loadedMovie.companiesList);
        movieView.setHomePage(loadedMovie.homepage);

        //loadImages(loadedMovie.id);
        loadCredits(currentMovie.id);
        loadTrailers(currentMovie.id);

        cashMovie();
    }

    private void cashMovie() {
        Picasso.with(getContext())
                .load(Url.getImage(currentMovie.posterPath, "original"))
                .into(target);
    }

    private void load() {
        movieView2.setPoster(currentMovie.posterPath)
                   .setTitle(currentMovie.title)
                   .setOverview(currentMovie.overview)
                   .setDate(currentMovie.releaseDate)
                   .setVoteAverage(currentMovie.voteAverage)
                   .setVoteCount(currentMovie.voteCount);
        // setfavbutton

        movieView.setOriginalTitle(currentMovie.originalTitle)
                 .setOriginalLang(currentMovie.originalLanguage);

        loadMovieDetails(currentMovie.id);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(() -> {
                String imageName = "/poster_" + currentMovie.id + ".jpg";

                File file = new File(AndroidUtils.getCacheDirectory() + imageName);
                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {}

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };

    private void loadTrailers(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<VideoResponse> call = service.getVideos(movieId, Url.TMDB_API_KEY, Url.en_US);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    trailersList.addAll(response.body().trailersList);
                    movieView.setTrailers(trailersList);
                } else {
                    // todo Error
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                // todo Error
            }
        });
    }

    private void loadImages(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<ImageResponse> call = service.getImages(movieId, Url.TMDB_API_KEY, null);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (response.isSuccessful()) {
                    List<Poster> posterList = response.body().postersList;
                    List<Backdrop> backdropList = response.body().backdropsList;

                    movieView.setImages(currentMovie.posterPath, currentMovie.backdropPath, posterList.size(), backdropList.size());
                } else {
                    // todo Error.
                }
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                // todo Error.
            }
        });
    }

    private void loadCredits(int movieId) {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(movieId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.isSuccessful()) {
                    movieView.setCrew(response.body().crewList);
                } else {
                    // todo Error.
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                // todo Error.
            }
        });
    }
}