package org.michaelbel.moviemade.modules_beta.movie;

import android.view.View;

import org.michaelbel.moviemade.data.dao.Company;
import org.michaelbel.moviemade.data.dao.Genre;
import org.michaelbel.moviemade.data.dao.Keyword;

public interface MovieViewListener {

    void onPosterClick(View view);

    void onFavoriteButtonClick(View view);
    void onWatchingButtonClick(View view);

    void onTrailerClick(View view, String trailerKey);
    void onTrailersSectionClick(View view);

    void onCompanyClick(View view, Company company);

    void onGenreClick(View view, Genre genre);
    void onGenresSectionClick(View view);

    void onKeywordClick(View view, Keyword keyword);

    void onCollectionClick(View view);

    void onMovieUrlClick(View view, int position);

    void onPostersClick(View view);
    void onBackdropsClick(View view);
}