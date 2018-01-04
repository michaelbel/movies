package org.michaelbel.application.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.model.Company;
import org.michaelbel.application.rest.model.Country;
import org.michaelbel.application.rest.model.Crew;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.view.section.GenresSectionView;
import org.michaelbel.application.ui.view.section.ImagesSectionView;
import org.michaelbel.application.ui.view.section.TrailersSectionView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import at.blogc.android.views.ExpandableTextView;
import io.realm.Realm;

public class MovieInfoLayout extends LinearLayout {

    private ImageView posterImageView;

    private RatingView ratingView;
    private TextView ratingTextView;
    private TextView voteCountTextView;

    private TextView dateTextView;

    private LinearLayout runtimeLayout;
    private TextView runtimeTextView;

    private LinearLayout countriesLayout;
    private TextView countriesTextView;

    private CheckedButton favoriteButton;
    private CheckedButton watchingButton;

    private LinearLayout titleTaglineLayout;
    private TextView titleTextView;
    private TextView taglineTextView;

    private LinearLayout overviewLayout;
    private ExpandableTextView overviewTextView;

    private TrailersSectionView trailersView;

    private ImagesSectionView imagesView;

    private LinearLayout crewLayout;
    private ProgressBar crewProgressBar;

    private TextView directorsTitle;
    private TextView directorsTextView;

    private TextView writersTitle;
    private TextView writersTextView;

    private TextView producersTitle;
    private TextView producersTextView;

    private LinearLayout infoLayout;
    private ProgressBar progressBar;

    private TextView originalTitleTitle;
    private TextView originalTitleTextView;

    private TextView originalLangTitle;
    private TextView originalLangTextView;

    private TextView statusTitle;
    private TextView statusTextView;

    private TextView budgetTitle;
    private TextView budgetTextView;

    private TextView revenueTitle;
    private TextView revenueTextView;

    private TextView companiesTitle;
    private TextView companiesTextView;

    private GenresSectionView genresView;

    private LinearLayout linksLayout;
    private MoviePageView linkTmdbView;
    private MoviePageView linkImdbView;
    private MoviePageView linkHomeView;

    private InfoMovieListener infoMovieListener;

    public interface InfoMovieListener {
        boolean onOverviewLongClick(View view);
        void onFavoriteButtonClick(View view);
        void onWatchingButtonClick(View view);
        void onTrailerClick(View view, String trailerKey);
        boolean onTrailerLongClick(View view, String trailerKey);
        void onTrailersSectionClick(View view);
        void onMovieUrlClick(View view, int position);
        void onGenreButtonClick(View view, Genre genre);
        void onGenresSectionClick(View view);
    }

    public MovieInfoLayout(Context context) {
        super(context);
        initialize(context);
    }

    public MovieInfoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));

        FrameLayout topLayout = new FrameLayout(context);
        topLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        topLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(topLayout);

//------POSTER IMAGE--------------------------------------------------------------------------------

        posterImageView = new ImageView(context);
        posterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImageView.setImageResource(R.drawable.movie_placeholder);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(posterImageView);

//--------------------------------------------------------------------------------------------------

        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(VERTICAL);
        layout1.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 120 + 32, 16, 16, 0));
        topLayout.addView(layout1);

//------RATING VIEW---------------------------------------------------------------------------------

        LinearLayout layout0 = new LinearLayout(context);
        layout0.setOrientation(HORIZONTAL);
        layout1.addView(layout0);

        ratingView = new RatingView(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        layout0.addView(ratingView);

        ratingTextView = new TextView(context);
        ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        ratingTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        ratingTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(ratingTextView);

        LinearLayout layout6 = new LinearLayout(context);
        layout6.setOrientation(HORIZONTAL);
        layout6.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(layout6);

        voteCountTextView = new TextView(context);
        voteCountTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        voteCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        voteCountTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM));
        layout6.addView(voteCountTextView);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(context, Theme.primaryTextColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(12, 12, Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        layout6.addView(voteCountIcon);

//------DATE VIEW-----------------------------------------------------------------------------------

        LinearLayout dateLayout = new LinearLayout(context);
        dateLayout.setOrientation(HORIZONTAL);
        dateLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(dateLayout);

        ImageView dateIcon = new ImageView(context);
        dateIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        dateIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        dateLayout.addView(dateIcon);

        dateTextView = new TextView(context);
        dateTextView.setMaxLines(1);
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        dateTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        dateTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        dateTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        dateLayout.addView(dateTextView);

//------RUNTIME VIEW--------------------------------------------------------------------------------

        runtimeLayout = new LinearLayout(context);
        runtimeLayout.setOrientation(HORIZONTAL);
        runtimeLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(runtimeLayout);

        ImageView clockIcon = new ImageView(context);
        clockIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_clock, ContextCompat.getColor(context, Theme.iconActiveColor())));
        clockIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        runtimeLayout.addView(clockIcon);

        runtimeTextView = new TextView(context);
        runtimeTextView.setMaxLines(1);
        runtimeTextView.setText(R.string.Loading);
        runtimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        runtimeTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        runtimeTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        runtimeTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        runtimeLayout.addView(runtimeTextView);

//------COUNTRIES VIEW------------------------------------------------------------------------------

        countriesLayout = new LinearLayout(context);
        countriesLayout.setOrientation(HORIZONTAL);
        countriesLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(countriesLayout);

        ImageView countriesIcon = new ImageView(context);
        countriesIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_earth, ContextCompat.getColor(context, Theme.iconActiveColor())));
        countriesIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START));
        countriesLayout.addView(countriesIcon);

        countriesTextView = new TextView(context);
        countriesTextView.setText(R.string.LoadingCountries);
        countriesTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        countriesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        countriesTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        countriesTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        countriesLayout.addView(countriesTextView);

//------FAVORITE AND WATCHLIST BUTTONS-----------------------------------------------------------------------------

        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        topLayout.addView(buttonsLayout);

        favoriteButton = new CheckedButton(context);
        favoriteButton.setStyle(CheckedButton.FAVORITE);
        favoriteButton.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onFavoriteButtonClick(view);
            }
        });
        favoriteButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(favoriteButton);

        watchingButton = new CheckedButton(context);
        watchingButton.setStyle(CheckedButton.WATCHING);
        watchingButton.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onWatchingButtonClick(view);
            }
        });
        watchingButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(watchingButton);

//------TITLE and TAGLINE---------------------------------------------------------------------------

        titleTaglineLayout = new LinearLayout(context);
        titleTaglineLayout.setOrientation(VERTICAL);
        titleTaglineLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(titleTaglineLayout);

        titleTextView = new TextView(context);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        titleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleTaglineLayout.addView(titleTextView);

        taglineTextView = new TextView(context);
        taglineTextView.setLines(1);
        taglineTextView.setText(R.string.LoadingTagline);
        taglineTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        taglineTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        titleTaglineLayout.addView(taglineTextView);

//------OVERVIEW------------------------------------------------------------------------------------

        overviewLayout = new LinearLayout(context);
        overviewLayout.setOrientation(VERTICAL);
        overviewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        overviewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(overviewLayout);

        overviewTextView = new ExpandableTextView(context);
        overviewTextView.setMaxLines(5);
        overviewTextView.setAnimationDuration(450L);
        overviewTextView.setEllipsize(TextUtils.TruncateAt.END);
        overviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        overviewTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        overviewTextView.setOnClickListener(v -> {
            if (!overviewTextView.isExpanded()) {
                overviewTextView.expand();
            }
        });
        overviewTextView.setOnLongClickListener(v -> {
            if (infoMovieListener != null) {
                infoMovieListener.onOverviewLongClick(v);
                return true;
            }
            return false;
        });
        overviewLayout.addView(overviewTextView);

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSectionView(context);
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        //addView(imagesView);

//------CREW VIEW-----------------------------------------------------------------------------------

        crewLayout = new LinearLayout(context);
        crewLayout.setOrientation(VERTICAL);
        crewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        crewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(crewLayout);

        FrameLayout crewTitleLayout = new FrameLayout(context);
        crewTitleLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, 48, 16, 0, 16, 0));
        crewLayout.addView(crewTitleLayout);

        TextView crewTitle = new TextView(context);
        crewTitle.setLines(1);
        crewTitle.setMaxLines(1);
        crewTitle.setSingleLine();
        crewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        crewTitle.setText(context.getString(R.string.Crew));
        crewTitle.setGravity(Gravity.CENTER_VERTICAL);
        crewTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        crewTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        crewTitle.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        crewTitleLayout.addView(crewTitle);

        crewProgressBar = new ProgressBar(context);
        crewProgressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.CENTER_VERTICAL));
        crewTitleLayout.addView(crewProgressBar);

        directorsTitle = new TextView(context);
        directorsTitle.setText(context.getString(R.string.Directors));
        directorsTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        directorsTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(directorsTitle);

        directorsTextView = new TextView(context);
        directorsTextView.setText(R.string.Loading);
        directorsTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        directorsTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(directorsTextView);

        writersTitle = new TextView(context);
        writersTitle.setText(context.getString(R.string.Writers));
        writersTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        writersTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(writersTitle);

        writersTextView = new TextView(context);
        writersTextView.setText(R.string.Loading);
        writersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        writersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(writersTextView);

        producersTitle = new TextView(context);
        producersTitle.setText(context.getString(R.string.Producers));
        producersTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        producersTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(producersTitle);

        producersTextView = new TextView(context);
        producersTextView.setText(R.string.Loading);
        producersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        producersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(producersTextView);

//------TRAILERS SECTION VIEW-----------------------------------------------------------------------

        trailersView = new TrailersSectionView(context);
        trailersView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        trailersView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onTrailersSectionClick(view);
            }
        });
        trailersView.setListener(new TrailersSectionView.SectionTrailersListener() {
            @Override
            public void onTrailerClick(View view, String trailerKey) {
                if (infoMovieListener != null) {
                    infoMovieListener.onTrailerClick(view, trailerKey);
                }
            }

            @Override
            public boolean onTrailerLongClick(View view, String trailerKey) {
                if (infoMovieListener != null) {
                    infoMovieListener.onTrailerLongClick(view, trailerKey);
                    return true;
                }
                return false;
            }
        });
        addView(trailersView);

//------INFO VIEW--------------------------------------------------------------------------------------------

        infoLayout = new LinearLayout(context);
        infoLayout.setOrientation(VERTICAL);
        infoLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
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

        progressBar = new ProgressBar(context);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.CENTER_VERTICAL));
        layout.addView(progressBar);

        originalTitleTitle = new TextView(context);
        originalTitleTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitleTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitleTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(originalTitleTitle);

        originalTitleTextView = new TextView(context);
        originalTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(originalTitleTextView);

        originalLangTitle = new TextView(context);
        originalLangTitle.setText(context.getString(R.string.OriginalLang));
        originalLangTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalLangTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(originalLangTitle);

        originalLangTextView = new TextView(context);
        originalLangTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalLangTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(originalLangTextView);

        statusTitle = new TextView(context);
        statusTitle.setText(context.getString(R.string.Status));
        statusTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        statusTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(statusTitle);

        statusTextView = new TextView(context);
        statusTextView.setText(R.string.Loading);
        statusTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        statusTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(statusTextView);

        budgetTitle = new TextView(context);
        budgetTitle.setText(context.getString(R.string.Budget));
        budgetTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        budgetTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(budgetTitle);

        budgetTextView = new TextView(context);
        budgetTextView.setText(R.string.Loading);
        budgetTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        budgetTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(budgetTextView);

        revenueTitle = new TextView(context);
        revenueTitle.setText(context.getString(R.string.Revenue));
        revenueTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        revenueTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(revenueTitle);

        revenueTextView = new TextView(context);
        revenueTextView.setText(R.string.Loading);
        revenueTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        revenueTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(revenueTextView);

//--------------------------------------------------------------------------------------------------

        companiesTitle = new TextView(context);
        companiesTitle.setText(context.getString(R.string.ProductionCompanies));
        companiesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        companiesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(companiesTitle);

        companiesTextView = new TextView(context);
        companiesTextView.setText(R.string.Loading);
        companiesTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        companiesTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 12));
        infoLayout.addView(companiesTextView);

//------GENRES--------------------------------------------------------------------------------------

        genresView = new GenresSectionView(context);
        genresView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        genresView.setOnClickListener(v -> {
            if (infoMovieListener != null) {
                infoMovieListener.onGenresSectionClick(v);
            }
        });
        genresView.setListener((view, genre) -> {
            if (infoMovieListener != null) {
                infoMovieListener.onGenreButtonClick(view, genre);
            }
        });
        addView(genresView);

//------WEB LINKS-----------------------------------------------------------------------------------

        linksLayout = new LinearLayout(context);
        linksLayout.setOrientation(VERTICAL);
        linksLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        linksLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(linksLayout);

        linkTmdbView = new MoviePageView(context);
        linkTmdbView.setText(R.string.ViewOnTMDb);
        linkTmdbView.setDivider(true);
        linkTmdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkTmdbView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 1);
            }
        });
        linksLayout.addView(linkTmdbView);

        linkImdbView = new MoviePageView(context);
        linkImdbView.setText(R.string.ViewOnIMDb);
        linkImdbView.setDivider(true);
        linkImdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkImdbView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 2);
            }
        });
        linksLayout.addView(linkImdbView);

        linkHomeView = new MoviePageView(context);
        linkHomeView.setText(R.string.ViewHomepage);
        linkHomeView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkHomeView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 3);
            }
        });
        linksLayout.addView(linkHomeView);
    }

//--------------------------------------------------------------------------------------------------

    public MovieInfoLayout setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_poster", "w342");

        Picasso.with(getContext())
               .load(Url.getImage(posterPath, size))
               .placeholder(R.drawable.movie_placeholder)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImageView);

        return this;
    }

    public MovieInfoLayout setFavoriteButton(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        Movie movieRealm = realm.where(Movie.class).equalTo("id", movieId).findFirst();
        if (movieRealm != null) {
            favoriteButton.setChecked(movieRealm.favorite);
        }
        return this;
    }

    public MovieInfoLayout setWatchingButton(int movieId) {
        Realm realm = Realm.getDefaultInstance();
        Movie movieRealm = realm.where(Movie.class).equalTo("id", movieId).findFirst();
        if (movieRealm != null) {
            watchingButton.setChecked(movieRealm.watching);
        }
        return this;
    }

    public MovieInfoLayout setTitle(@NonNull String movieTitle) {
        titleTextView.setText(movieTitle.isEmpty() ? "-" : movieTitle);
        return this;
    }

    public MovieInfoLayout setDate(@NonNull String releaseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(releaseDate);
        } catch (ParseException e) {
            // todo Error.
        }

        dateTextView.setText(releaseDate.isEmpty() ? "-" : newFormat.format(date));
        return this;
    }

    public MovieInfoLayout setRuntime(int runTime) {
        if (runTime == -1) {
            removeView(runtimeLayout);
        } else {
            SimpleDateFormat formatMin = new SimpleDateFormat("m", Locale.getDefault());
            SimpleDateFormat formatHours = new SimpleDateFormat("H:mm", Locale.getDefault());

            Date date;
            String str = null;

            try {
                date = formatMin.parse(String.valueOf(runTime));
                str = formatHours.format(date);
            } catch (ParseException e) {
                // todo Error.
            }

            runtimeTextView.setText(runTime == 0 ? "-" : getContext().getString(R.string.RuntimeMin, runTime) + " / " + str);
        }

        return this;
    }

    public MovieInfoLayout setCountries(@NonNull List<Country> countries) {
        if (countries == null || countries.isEmpty()) {
            removeView(countriesLayout);
        } else {
            StringBuilder text = new StringBuilder();
            for (Country country : countries) {
                if (country.name.equals("United States of America")) {
                    country.name = "USA";
                } else if (country.name.equals("United Kingdom")) {
                    country.name = "UK";
                } else if (country.name.equals("United Arab Emirates")) {
                    country.name = "UAE";
                }

                text.append(country.name);
                if (country != countries.get(countries.size() - 1)) {
                    text.append(", ");
                }
            }

            countriesTextView.setText(text);
        }

        return this;
    }

    public MovieInfoLayout setTagline(@NonNull String tagline) {
        if (tagline == null || tagline.isEmpty()) {
            titleTaglineLayout.removeView(taglineTextView);
        } else {
            taglineTextView.setText(tagline);
        }

        return this;
    }

    public MovieInfoLayout setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingTextView.setText(String.valueOf(voteAverage));
        return this;
    }

    public MovieInfoLayout setVoteCount(int voteCount) {
        voteCountTextView.setText(String.valueOf(voteCount));
        return this;
    }

    public MovieInfoLayout setOverview(@NonNull String overview) {
        if (overview == null || overview.isEmpty()) {
            removeView(overviewLayout);
        } else {
            overviewTextView.setText(overview);
        }

        return this;
    }

    public MovieInfoLayout setStatus(@NonNull String status) {
        if (status == null || status.isEmpty()) {
            infoLayout.removeView(statusTitle);
            infoLayout.removeView(statusTextView);
        } else {
            statusTextView.setText(status);
        }

        onInfoLoad();
        return this;
    }

    public MovieInfoLayout setBudget(int budget) {
        if (budget == 0) {
            infoLayout.removeView(budgetTitle);
            infoLayout.removeView(budgetTextView);
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            budgetTextView.setText(getContext().getString(R.string.MoneyCount, formatter.format(budget)));
        }

        onInfoLoad();
        return this;
    }

    public MovieInfoLayout setRevenue(int revenue) {
        if (revenue == 0) {
            infoLayout.removeView(revenueTitle);
            infoLayout.removeView(revenueTextView);
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            revenueTextView.setText(getContext().getString(R.string.MoneyCount, formatter.format(revenue)));
        }

        onInfoLoad();
        return this;
    }

    public MovieInfoLayout setOriginalTitle(@NonNull String originalTitle) {
        if (originalTitle == null || originalTitle.isEmpty()) {
            infoLayout.removeView(originalTitleTitle);
            infoLayout.removeView(originalTitleTextView);
        } else {
            originalTitleTextView.setText(originalTitle);
        }

        return this;
    }

    public MovieInfoLayout setOriginalLanguage(@Nullable String lang) {
        if (lang == null || lang.isEmpty()) {
            infoLayout.removeView(originalLangTitle);
            infoLayout.removeView(originalLangTextView);
        } else {
            if (Objects.equals(lang, "en")) {
                lang = "English";
            }

            originalLangTextView.setText(lang);
        }

        return this;
    }

    public MovieInfoLayout setCompanies(@NonNull List<Company> companiesList) {
        if (companiesList == null || companiesList.isEmpty()) {
            infoLayout.removeView(companiesTitle);
            infoLayout.removeView(companiesTextView);
        } else {
            StringBuilder text = new StringBuilder();
            for (Company company : companiesList) {
                text.append(company.name);
                if (company != companiesList.get(companiesList.size() - 1)) {
                    text.append(", ");
                }
            }

            companiesTextView.setText(text);
        }

        onInfoLoad();
        return this;
    }

    public MovieInfoLayout setHomePage(@NonNull String homePage) {
        if (homePage == null || homePage.isEmpty()) {
            linksLayout.removeView(linkHomeView);
            linkImdbView.setDivider(false);
        }

        return this;
    }

    public MovieInfoLayout setCrew(@NonNull List<Crew> crewList) {
        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> producers = new ArrayList<>();

        for (Crew crew : crewList) {
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
            directorsTextView.setText(text1.toString());
        } else {
            crewLayout.removeView(directorsTitle);
            crewLayout.removeView(directorsTextView);
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
        } else {
            crewProgressBar.setVisibility(INVISIBLE);
        }

        return this;
    }

    public MovieInfoLayout setTrailers(@NonNull List<Trailer> list) {
        if (list.isEmpty()) {
            removeView(trailersView);
        } else {
            trailersView.setTrailers(list);
            trailersView.getProgressBar().setVisibility(INVISIBLE);
        }

        return this;
    }

    public MovieInfoLayout setGenres(@NonNull List<Genre> genresList) {
        if (genresList.isEmpty()) {
            removeView(genresView);
        } else {
            genresView.setGenres(genresList);
            genresView.getProgressBar().setVisibility(INVISIBLE);
        }

        return this;
    }

    public MovieInfoLayout setImages(@NonNull String poster, @NonNull String backdrop, int postersCount, int backdropsCount) {
        imagesView.setPoster(poster);
        imagesView.setBackdrop(backdrop);
        imagesView.setPostersCount(postersCount);
        imagesView.setBackdropsCount(backdropsCount);
        return this;
    }

    public void setListener(@NonNull InfoMovieListener listener) {
        infoMovieListener = listener;
    }

    private void onInfoLoad() {
        if (statusTextView.getText().toString().equals("Loading...") ||
                budgetTextView.getText().toString().equals("Loading...") ||
                revenueTextView.getText().toString().equals("Loading...") ||
                companiesTextView.getText().toString().equals("Loading...")) {
            progressBar.setVisibility(VISIBLE);
        } else {
            progressBar.setVisibility(INVISIBLE);
        }
    }
}