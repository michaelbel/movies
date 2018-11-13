package org.michaelbel.moviemade.modules_beta.view;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureImageView;

import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.ui.modules.movie.views.RatingView;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.extensions.AndroidExtensions;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.modules_beta.movie.MovieViewListener;
import org.michaelbel.moviemade.modules_beta.view.section.CompaniesSection;
import org.michaelbel.moviemade.modules_beta.view.section.GenresSection;
import org.michaelbel.moviemade.modules_beta.view.section.ImagesSection;
import org.michaelbel.moviemade.modules_beta.view.section.KeywordsSection;
import org.michaelbel.moviemade.modules_beta.view.section.TrailersSection;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.blogc.android.views.ExpandableTextView;

public class MovieViewLayout extends LinearLayout {

    private ProgressBar topProgressBar;
    private GestureImageView posterImage;

    private LinearLayout shortInfoLayout;

    private TextView ratingText;
    private RatingView ratingView;

    private LinearLayout voteCountLayout;
    private TextView voteCountText;

    private LinearLayout releaseDateLayout;
    private TextView releaseDateText;

    private LinearLayout runtimeLayout;
    private TextView runtimeText;

    private LinearLayout langLayout;
    private TextView originalLanguageText;

    private LinearLayout buttonsLayout;
    private CheckedButton favoriteButton;
    private CheckedButton watchingButton;

    private LinearLayout titleTaglineLayout;

    private TextView titleText;
    private TextView taglineText;

    private LinearLayout overviewLayout;
    private ExpandableTextView overviewText;

    private TrailersSection trailersView;

    private ImagesSection imagesView;

    private LinearLayout crewLayout;

    private TextView directorsTitle;
    private TextView directorsText;

    private TextView writersTitle;
    private TextView writersTextView;

    private TextView producersTitle;
    private TextView producersTextView;

    private LinearLayout infoLayout;

    private TextView originalTitle;
    private TextView originalTitleText;

    private TextView statusTitle;
    private TextView statusTextView;

    private TextView budgetTitle;
    private TextView budgetText;

    private TextView revenueTitle;
    private TextView revenueText;

    private TextView countriesTitle;
    private TextView countriesText;

    private TextView companiesTitle;
    private CompaniesSection companiesView;

    private GenresSection genresView;

    private KeywordsSection keywordsView;

    private CollectionView collectionView;

    private LinearLayout linksLayout;
    private WebpageView linkTmdbView;
    private WebpageView linkImdbView;
    private WebpageView linkHomeView;

    private MovieViewListener movieViewListener;

    public MovieViewLayout(Context context) {
        super(context);

//------FAVORITE AND WATCHLIST BUTTONS--------------------------------------------------------------

        buttonsLayout = new LinearLayout(context);
        buttonsLayout.setVisibility(INVISIBLE);
        buttonsLayout.setOrientation(HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        //topLayout.addView(buttonsLayout);

        favoriteButton = new CheckedButton(context);
        favoriteButton.setVisibility(INVISIBLE);
        favoriteButton.setStyle(CheckedButton.FAVORITE);
        favoriteButton.setOnClickListener(view -> movieViewListener.onFavoriteButtonClick(view));
        favoriteButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(favoriteButton);

        watchingButton = new CheckedButton(context);
        watchingButton.setVisibility(INVISIBLE);
        watchingButton.setStyle(CheckedButton.WATCHING);
        watchingButton.setOnClickListener(view -> movieViewListener.onWatchingButtonClick(view));
        watchingButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(watchingButton);

//------OVERVIEW------------------------------------------------------------------------------------

        overviewLayout = new LinearLayout(context);
        overviewLayout.setOrientation(VERTICAL);
        overviewLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
        overviewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(overviewLayout);

        overviewText = (ExpandableTextView) LayoutInflater.from(context).inflate(R.layout.item_overview, null);
        overviewText.setMaxLines(5);
        overviewText.setAnimationDuration(350L);
        overviewText.setTextIsSelectable(false);
        overviewText.setEllipsize(TextUtils.TruncateAt.END);
        overviewText.setTextIsSelectable(AndroidUtils.textSelect());
        overviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        overviewText.setInterpolator(new OvershootInterpolator(0));
        overviewText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        overviewText.setOnClickListener(view -> overviewText.toggle());
        /*overviewText.setOnLongClickListener(view -> {
            movieViewListener.onOverviewLongClick(view);
            return true;
        });*/
        overviewLayout.addView(overviewText);

        if (AndroidUtils.fullOverview()) {
            overviewText.setMaxLines(1000);
            overviewText.setOnClickListener(null);
        }

//------CREW VIEW-----------------------------------------------------------------------------------

        crewLayout = new LinearLayout(context);
        crewLayout.setOrientation(VERTICAL);
        crewLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
        crewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(crewLayout);

        FrameLayout crewTitleLayout = new FrameLayout(context);
        crewTitleLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 48, 16, 0, 16, 0));
        crewLayout.addView(crewTitleLayout);

        TextView crewTitle = new TextView(context);
        crewTitle.setLines(1);
        crewTitle.setMaxLines(1);
        crewTitle.setSingleLine();
        crewTitle.setText(context.getString(R.string.Crew));
        crewTitle.setGravity(Gravity.CENTER_VERTICAL);
        crewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        crewTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        crewTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        crewTitle.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        crewTitleLayout.addView(crewTitle);

        directorsTitle = new TextView(context);
        directorsTitle.setText(context.getString(R.string.Directors));
        directorsTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        directorsTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(directorsTitle);

        directorsText = new TextView(context);
        directorsText.setText(R.string.loading_status);
        directorsText.setTextIsSelectable(AndroidUtils.textSelect());
        directorsText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        directorsText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(directorsText);

        writersTitle = new TextView(context);
        writersTitle.setText(context.getString(R.string.Writers));
        writersTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        writersTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(writersTitle);

        writersTextView = new TextView(context);
        writersTextView.setText(R.string.loading_status);
        writersTextView.setTextIsSelectable(AndroidUtils.textSelect());
        writersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        writersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(writersTextView);

        producersTitle = new TextView(context);
        producersTitle.setText(context.getString(R.string.Producers));
        producersTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        producersTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(producersTitle);

        producersTextView = new TextView(context);
        producersTextView.setText(R.string.loading_status);
        producersTextView.setTextIsSelectable(AndroidUtils.textSelect());
        producersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        producersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(producersTextView);

//------TRAILERS SECTION VIEW-----------------------------------------------------------------------

        trailersView = new TrailersSection(context);
        trailersView.setVisibility(INVISIBLE);
        trailersView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        trailersView.setOnClickListener(view -> movieViewListener.onTrailersSectionClick(view));
        trailersView.setListener(new TrailersSection.SectionTrailersListener() {
            @Override
            public void onTrailerClick(View view, String trailerKey) {
                movieViewListener.onTrailerClick(view, trailerKey);
            }

            @Override
            public void onTrailerLongClick(View view, String trailerKey) {
                /*if (movieViewListener != null) {
                    movieViewListener.onTrailerLongClick(view, trailerKey);
                    return true;
                }*/
            }
        });
        //addView(trailersView);

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSection(context);
        imagesView.setVisibility(INVISIBLE);
        imagesView.getPostersLayout().setOnClickListener(view -> movieViewListener.onPostersClick(view));
        imagesView.getBackdropsLayout().setOnClickListener(view -> movieViewListener.onBackdropsClick(view));
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        //addView(imagesView);

//------INFO VIEW-----------------------------------------------------------------------------------

        infoLayout = new LinearLayout(context);
        infoLayout.setOrientation(VERTICAL);
        infoLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
        infoLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(infoLayout);

        FrameLayout layout = new FrameLayout(context);
        layout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 48, 16, 0, 16, 0));
        infoLayout.addView(layout);

        TextView infoTitle = new TextView(context);
        infoTitle.setLines(1);
        infoTitle.setMaxLines(1);
        infoTitle.setSingleLine();
        infoTitle.setText(context.getString(R.string.Info));
        infoTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        infoTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        infoTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        infoTitle.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        layout.addView(infoTitle);

        originalTitle = new TextView(context);
        originalTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(originalTitle);

        originalTitleText = new TextView(context);
        originalTitleText.setTextIsSelectable(AndroidUtils.textSelect());
        originalTitleText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(originalTitleText);

        statusTitle = new TextView(context);
        statusTitle.setText(context.getString(R.string.Status));
        statusTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        statusTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(statusTitle);

        statusTextView = new TextView(context);
        statusTextView.setText(R.string.loading_status);
        statusTextView.setTextIsSelectable(AndroidUtils.textSelect());
        statusTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        statusTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(statusTextView);

        budgetTitle = new TextView(context);
        budgetTitle.setText(context.getString(R.string.Budget));
        budgetTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        budgetTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(budgetTitle);

        budgetText = new TextView(context);
        budgetText.setText(R.string.loading_status);
        budgetText.setTextIsSelectable(AndroidUtils.textSelect());
        budgetText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        budgetText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(budgetText);

        revenueTitle = new TextView(context);
        revenueTitle.setText(context.getString(R.string.Revenue));
        revenueTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        revenueTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(revenueTitle);

        revenueText = new TextView(context);
        revenueText.setText(R.string.loading_status);
        revenueText.setTextIsSelectable(AndroidUtils.textSelect());
        revenueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        revenueText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(revenueText);

        countriesTitle = new TextView(context);
        countriesTitle.setText(context.getString(R.string.CountriesTitle));
        countriesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        countriesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(countriesTitle);

        countriesText = new TextView(context);
        countriesText.setTextIsSelectable(AndroidUtils.textSelect());
        countriesText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        countriesText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(countriesText);

        companiesTitle = new TextView(context);
        companiesTitle.setText(context.getString(R.string.ProductionCompanies));
        companiesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        companiesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(companiesTitle);

        companiesView = new CompaniesSection(context);
        companiesView.setListener((view, company) -> movieViewListener.onCompanyClick(view, company));
        companiesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 10F, 2, 12.0F, 12));
        infoLayout.addView(companiesView);

//------GENRES--------------------------------------------------------------------------------------

        genresView = new GenresSection(context);
        genresView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        genresView.setOnClickListener(view -> movieViewListener.onGenresSectionClick(view));
        genresView.setListener((view, genre) -> movieViewListener.onGenreClick(view, genre));
        addView(genresView);

//------KEYWORDS------------------------------------------------------------------------------------

        keywordsView = new KeywordsSection(context);
        keywordsView.setListener((view, keyword) -> movieViewListener.onKeywordClick(view, keyword));
        keywordsView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(keywordsView);

//------WEB LINKS-----------------------------------------------------------------------------------

        linksLayout = new LinearLayout(context);
        linksLayout.setOrientation(VERTICAL);
        linksLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        linksLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(linksLayout);

        linkTmdbView = new WebpageView(context);
        linkTmdbView.setText(R.string.ViewOnTMDb);
        linkTmdbView.setDivider(true);
        linkTmdbView.setOnClickListener(view -> movieViewListener.onMovieUrlClick(view, 1));
        linkTmdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linksLayout.addView(linkTmdbView);

        linkImdbView = new WebpageView(context);
        linkImdbView.setText(R.string.ViewOnIMDb);
        linkImdbView.setDivider(true);
        linkImdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkImdbView.setOnClickListener(view -> movieViewListener.onMovieUrlClick(view, 2));
        linksLayout.addView(linkImdbView);

        linkHomeView = new WebpageView(context);
        linkHomeView.setText(R.string.ViewHomepage);
        linkHomeView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkHomeView.setOnClickListener(view -> movieViewListener.onMovieUrlClick(view, 3));
        linksLayout.addView(linkHomeView);

//------COLLECTION VIEW-----------------------------------------------------------------------------

        collectionView = new CollectionView(context);
        collectionView.setOnClickListener(view -> movieViewListener.onCollectionClick(view));
        collectionView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 6));
        addView(collectionView);

        FrameLayout admobLayout = new FrameLayout(context);
        admobLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
    }

    public void addTitle(String title) {
        if (title == null || title.isEmpty()) {
            titleText.setText("-");
            return;
        }

        titleText.setText(title);
        titleText.setVisibility(VISIBLE);
    }

    public void addTagline(String tagline) {
        if (tagline == null || tagline.isEmpty()) {
            titleTaglineLayout.removeView(taglineText);
            return;
        }

        taglineText.setText(tagline);
        taglineText.setVisibility(VISIBLE);
    }

    public void addOverview(String overview) {
        if (overview == null || overview.isEmpty()) {
            removeView(overviewLayout);
            return;
        }

        overviewText.setText(overview);
    }

    public void addReleaseDate(String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty()) {
            releaseDateText.setText("-");
            return;
        }

        releaseDateText.setText(releaseDate);
        releaseDateLayout.setVisibility(VISIBLE);
    }

    public void addOriginalTitle(String title) {
        if (title == null || title.isEmpty()) {
            infoLayout.removeView(originalTitle);
            infoLayout.removeView(originalTitleText);
            return;
        }

        originalTitleText.setText(title);
    }

    public void addOriginalLanguage(String origLang) {
        if (origLang == null || origLang.isEmpty()) {
            shortInfoLayout.removeView(langLayout);
            return;
        }

        originalLanguageText.setText(origLang);
        langLayout.setVisibility(VISIBLE);
    }

    public void addImages(List<Poster> posters, List<Backdrop> backdrops, int postersSize, int backdropsSize) {
        if (posters == null || posters.isEmpty() || backdrops == null || backdrops.isEmpty()) {
            removeView(imagesView);
            return;
        }

        imagesView.addPosters(posters, postersSize);
        imagesView.addBackdrops(backdrops, backdropsSize);
        imagesView.setVisibility(VISIBLE);
    }

    public void addRuntime(int runtime) {
        if (runtime == 0) {
            shortInfoLayout.removeView(runtimeLayout);
            return;
        }

        runtimeText.setText(AndroidExtensions.formatRuntime(runtime));
        runtimeLayout.setVisibility(VISIBLE);
    }

    public void addRuntime(String runtime) {
        if (runtime == null || runtime.isEmpty()) {
            shortInfoLayout.removeView(runtimeLayout);
            return;
        }

        runtimeText.setText(runtime);
        runtimeLayout.setVisibility(VISIBLE);
    }

    public void addStatus(String status) {
        if (status == null || status.isEmpty()) {
            infoLayout.removeView(statusTitle);
            infoLayout.removeView(statusTextView);
            return;
        }

        statusTextView.setText(status);
    }

    public void addBudget(int budget) {
        if (budget == 0) {
            infoLayout.removeView(budgetTitle);
            infoLayout.removeView(budgetText);
            return;
        }

        budgetText.setText(AndroidUtils.formatCurrency(budget));
    }

    public void addBudget(String budget) {
        if (budget == null || budget.isEmpty()) {
            infoLayout.removeView(budgetTitle);
            infoLayout.removeView(budgetText);
            return;
        }

        budgetText.setText(budget);
    }

    public void addRevenue(int revenue) {
        if (revenue == 0) {
            infoLayout.removeView(revenueTitle);
            infoLayout.removeView(revenueText);
            return;
        }

        revenueText.setText(AndroidUtils.formatCurrency(revenue));
    }

    public void addRevenue(String revenue) {
        if (revenue == null || revenue.isEmpty()) {
            infoLayout.removeView(revenueTitle);
            infoLayout.removeView(revenueText);
            return;
        }

        revenueText.setText(revenue);
    }

    public void addImdbpage(String imdbpage) {
        if (imdbpage == null || imdbpage.isEmpty()) {
            linksLayout.removeView(linkImdbView);
            linkTmdbView.setDivider(false);
        }
    }

    public void addHomepage(String homepage) {
        if (homepage == null || homepage.isEmpty()) {
            linksLayout.removeView(linkHomeView);
            linkImdbView.setDivider(false);
        }
    }

    public void addCountries(String countries) {
        if (countries == null || countries.isEmpty()) {
            infoLayout.removeView(countriesTitle);
            infoLayout.removeView(countriesText);
            return;
        }

        countriesText.setText(countries);
    }

    public void addCompanies(List<Company> companies) {
        if (companies == null || companies.isEmpty()) {
            infoLayout.removeView(companiesTitle);
            infoLayout.removeView(companiesView);
            return;
        }

        companiesView.setCompanies(companies);
    }

    public void favoriteButtonVisibility(int visibility) {
        favoriteButton.setVisibility(visibility);
    }

    public void watchingButtonVisibility(int visibility) {
        watchingButton.setVisibility(visibility);
    }

    public void setFavoriteButton(boolean favorite) {
        favoriteButton.setChecked(favorite);
    }

    public void setWatchingButton(boolean watching) {
        watchingButton.setChecked(watching);
    }

    public void addTrailers(List<Video> trailers) {
        if (trailers == null || trailers.isEmpty()) {
            removeView(trailersView);
            return;
        }

        trailersView.setTrailers(trailers);
        trailersView.getProgressBar().setVisibility(INVISIBLE);
        trailersView.setVisibility(VISIBLE);
    }

    public void addMovieViewListener(@NonNull MovieViewListener listener) {
        movieViewListener = listener;
    }

    public void addGenres(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            removeView(genresView);
            return;
        }

        genresView.setGenres(genres);
        genresView.getProgressBar().setVisibility(INVISIBLE);
    }

    public void addKeywords(List<Keyword> keywords) {
        if (keywords == null || keywords.isEmpty()) {
            removeView(keywordsView);
            return;
        }

        keywordsView.setKeywords(keywords);
        keywordsView.getProgressBar().setVisibility(GONE);
    }

    public void setCrew(List<Crew> crews) {
        if (crews == null) {
            removeView(crewLayout);
            return;
        }

        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> producers = new ArrayList<>();

        for (Crew crew : crews) {
            switch (crew.department) {
                case "Directing":
                    directors.add(crew.name);
                    break;
                case "Writing":
                    writers.add(crew.name);
                    break;
                case "Production":
                    producers.add(crew.name);
                    break;
            }
        }

        StringBuilder text1 = new StringBuilder();
        for (String director : directors) {
            text1.append(director);
            if (!Objects.equals(director, directors.get(directors.size() - 1))) {
                text1.append(", ");
            }
        }

        if (!text1.toString().isEmpty()) {
            directorsText.setText(text1.toString());
        } else {
            crewLayout.removeView(directorsTitle);
            crewLayout.removeView(directorsText);
        }

        StringBuilder text2 = new StringBuilder();
        for (String writer : writers) {
            text2.append(writer);
            if (!Objects.equals(writer, writers.get(writers.size() - 1))) {
                text2.append(", ");
            }
        }

        if (!text2.toString().isEmpty()) {
            writersTextView.setText(text2.toString());
        } else {
            crewLayout.removeView(writersTitle);
            crewLayout.removeView(writersTextView);
        }

        StringBuilder text3 = new StringBuilder();
        for (String producer : producers) {
            text3.append(producer);
            if (!Objects.equals(producer, producers.get(producers.size() - 1))) {
                text3.append(", ");
            }
        }

        if (!text3.toString().isEmpty()) {
            producersTextView.setText(text3.toString());
        } else {
            crewLayout.removeView(producersTitle);
            crewLayout.removeView(producersTextView);
        }

        if (text1.toString().isEmpty() && text2.toString().isEmpty() && text3.toString().isEmpty()) {
            removeView(crewLayout);
        } /*else {
            crewProgressBar.setVisibility(INVISIBLE);
        }*/
    }

    public void addCollection(Collection collection) {
        if (collection == null) {
            removeView(collectionView);
            return;
        }

        collectionView.addName(getContext().getString(R.string.PartOfCollection, collection.name));
        collectionView.addImage(collection.backdropPath);
    }

    public void topLayoutLoaded() {
        topProgressBar.setVisibility(GONE);

        posterImage.setVisibility(VISIBLE);
        shortInfoLayout.setVisibility(VISIBLE);
        buttonsLayout.setVisibility(VISIBLE);
        titleTaglineLayout.setVisibility(VISIBLE);
    }

    // Getters:

    /*public ImagesSection getImagesView() {
        return imagesView;
    }*/

    public TrailersSection getTrailersView() {
        return trailersView;
    }

    public GenresSection getGenresView() {
        return genresView;
    }

    /*public KeywordsSection getKeywordsView() {
        return keywordsView;
    }*/

    public GestureImageView getPoster() {
        return posterImage;
    }

    public List<Keyword> getKeywords() {
        return keywordsView.getKeywords();
    }

    public List<Company> getCompanies() {
        return companiesView.getCompanies();
    }
}