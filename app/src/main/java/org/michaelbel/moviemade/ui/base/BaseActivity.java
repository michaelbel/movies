package org.michaelbel.moviemade.ui.base;

import android.content.Intent;
import android.view.View;

import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.Collection;
import org.michaelbel.moviemade.data.dao.Company;
import org.michaelbel.moviemade.data.dao.Genre;
import org.michaelbel.moviemade.data.dao.Keyword;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Person;
import org.michaelbel.moviemade.data.dao.Review;
import org.michaelbel.moviemade.model.MovieRealm;
import org.michaelbel.moviemade.modules_beta.collection.CollectionActivity;
import org.michaelbel.moviemade.modules_beta.company.CompanyActivity;
import org.michaelbel.moviemade.modules_beta.genres.GenreActivity;
import org.michaelbel.moviemade.modules_beta.genres.GenresActivity;
import org.michaelbel.moviemade.modules_beta.keywords.KeywordActivity;
import org.michaelbel.moviemade.modules_beta.person.PersonActivity;
import org.michaelbel.moviemade.modules_beta.review.ReviewActivity;
import org.michaelbel.moviemade.moxy.MvpAppCompatActivity;
import org.michaelbel.moviemade.ui.modules.movie.MovieActivity;
import org.michaelbel.moviemade.ui.modules.reviews.ReviewsActivity;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersActivity;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;

@SuppressWarnings("registered")
public class BaseActivity extends MvpAppCompatActivity implements BaseMvp, MediaMvp {

    @Override
    public void startFragment(Fragment fragment, View container) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(container.getId(), fragment)
            .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(containerId, fragment)
            .commitAllowingStateLoss();
    }

    @Override
    public void startFragment(Fragment fragment, View container, String tag) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(container.getId(), fragment)
            .addToBackStack(tag)
            .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId, String tag) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(tag)
            .commit();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager()
            .popBackStack();
    }

//--MediaMvp--------------------------------------------------------------------------------------

    @Override
    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void startTrailers(Movie movie) {
        Intent intent = new Intent(this, TrailersActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("title", movie.getTitle());
        startActivity(intent);
    }

    @Override
    public void startReviews(Movie movie) {
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putExtra("id", movie.getId());
        intent.putExtra("title", movie.getTitle());
        startActivity(intent);
    }



    @Override
    public void startMovie(MovieRealm movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        //intent.putExtra("movieRealm", movie);
        startActivity(intent);
    }

    @Override
    public void startPerson(Cast person) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("cast_person", person);
        startActivity(intent);
    }

    @Override
    public void startPerson(Person person) {
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra("people_person", person);
        startActivity(intent);
    }

    @Override
    public void startReview(Review review, Movie movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("review", review);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public void startReview(Review review, MovieRealm movie) {
        Intent intent = new Intent(this, ReviewActivity.class);
        intent.putExtra("review", review);
        //intent.putExtra("movieRealm", movie);
        startActivity(intent);
    }

    @Override
    public void startGenre(Genre genre) {
        Intent intent = new Intent(this, GenreActivity.class);
        intent.putExtra("genre", genre);
        startActivity(intent);
    }

    @Override
    public void startGenres(ArrayList<Genre> list) {
        Intent intent = new Intent(this, GenresActivity.class);
        //intent.putParcelableArrayListExtra("list", list);
        startActivity(intent);
    }

    @Override
    public void startKeyword(Keyword keyword) {
        Intent intent = new Intent(this, KeywordActivity.class);
        intent.putExtra("keyword", keyword);
        startActivity(intent);
    }

    @Override
    public void startCollection(Collection collection) {
        Intent intent = new Intent(this, CollectionActivity.class);
        //intent.putExtra("collection", collection);
        startActivity(intent);
    }

    @Override
    public void startCompany(Company company) {
        Intent intent = new Intent(this, CompanyActivity.class);
        intent.putExtra("company", company);
        startActivity(intent);
    }
}