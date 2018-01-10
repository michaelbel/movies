package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
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

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.rest.model.BelongsToCollection;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.Genre;
import org.michaelbel.moviemade.rest.model.Trailer;
import org.michaelbel.moviemade.ui.PlaceholderView;
import org.michaelbel.moviemade.ui.view.section.GenresSectionView;
import org.michaelbel.moviemade.ui.view.section.ImagesSectionView;
import org.michaelbel.moviemade.ui.view.section.TrailersSectionView;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieViewLayout extends LinearLayout {

    private PlaceholderView phPoster;
    private ImageView posterImageView;

    private LinearLayout shortInfoLayout;
    private RatingView ratingView;
    private TextView ratingTextView;

    private LinearLayout voteCountLayout;
    private TextView voteCountTextView;

    private LinearLayout releaseDateLayout;
    private TextView releaseDateText;

    private LinearLayout runtimeLayout;
    private TextView runtimeText;

    private LinearLayout langLayout;
    private TextView originalLanguageText;

    private CheckedButton favoriteButton;
    private CheckedButton watchingButton;

    private LinearLayout titleTaglineLayout;
    private TextView titleText;
    private TextView taglineTextView;

    private LinearLayout overviewLayout;
    private TextView overviewTextView;

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

    private TextView contriesTitle;
    private TextView contriesText;

    private TextView statusTitle;
    private TextView statusTextView;

    private TextView budgetTitle;
    private TextView budgetText;

    private TextView revenueTitle;
    private TextView revenueText;

    private TextView companiesTitle;
    private TextView companiesTextView;

    private GenresSectionView genresView;

    private BelongCollectionView collectionView;

    private LinearLayout linksLayout;
    private WebpageView linkTmdbView;
    private WebpageView linkImdbView;
    private WebpageView linkHomeView;

    private MovieViewListener movieViewListener;

    public interface MovieViewListener {
        boolean onOverviewLongClick(View view);
        //void onFavoriteButtonClick(View view);
        //void onWatchingButtonClick(View view);
        void onTrailerClick(View view, String trailerKey);
        //boolean onTrailerLongClick(View view, String trailerKey);
        void onTrailersSectionClick(View view);
        void onMovieUrlClick(View view, int position);
        void onGenreSelected(View view, Genre genre);
        //void onGenresSectionClick(View view);
    }

    public MovieViewLayout(Context context) {
        super(context);

        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));

        FrameLayout topLayout = new FrameLayout(context);
        topLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        topLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(topLayout);

//------POSTER IMAGE--------------------------------------------------------------------------------

        phPoster = new PlaceholderView(context);
        phPoster.show();
        phPoster.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(phPoster);

        posterImageView = new ImageView(context);
        posterImageView.setVisibility(INVISIBLE);
        posterImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImageView.setImageResource(R.drawable.movie_placeholder_old);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(posterImageView);

//--------------------------------------------------------------------------------------------------

        shortInfoLayout = new LinearLayout(context);
        shortInfoLayout.setOrientation(VERTICAL);
        shortInfoLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 120 + 32, 16, 16, 0));
        topLayout.addView(shortInfoLayout);

//------RATING VIEW---------------------------------------------------------------------------------

        LinearLayout layout0 = new LinearLayout(context);
        layout0.setOrientation(HORIZONTAL);
        shortInfoLayout.addView(layout0);

        ratingView = new RatingView(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        layout0.addView(ratingView);

        ratingTextView = new TextView(context);
        ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        ratingTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        ratingTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(ratingTextView);

        voteCountLayout = new LinearLayout(context);
        voteCountLayout.setOrientation(HORIZONTAL);
        voteCountLayout.setVisibility(INVISIBLE);
        voteCountLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(voteCountLayout);

        voteCountTextView = new TextView(context);
        voteCountTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        voteCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        voteCountTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM));
        voteCountLayout.addView(voteCountTextView);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(context, Theme.primaryTextColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(12, 12, Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        voteCountLayout.addView(voteCountIcon);

//------DATE VIEW-----------------------------------------------------------------------------------

        releaseDateLayout = new LinearLayout(context);
        releaseDateLayout.setOrientation(HORIZONTAL);
        releaseDateLayout.setVisibility(INVISIBLE);
        releaseDateLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(releaseDateLayout);

        ImageView dateIcon = new ImageView(context);
        dateIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        dateIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        releaseDateLayout.addView(dateIcon);

        releaseDateText = new TextView(context);
        releaseDateText.setMaxLines(1);
        releaseDateText.setText("Loading...");
        releaseDateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        releaseDateText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        releaseDateText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        releaseDateText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        releaseDateLayout.addView(releaseDateText);

//------RUNTIME VIEW--------------------------------------------------------------------------------

        runtimeLayout = new LinearLayout(context);
        runtimeLayout.setOrientation(HORIZONTAL);
        runtimeLayout.setVisibility(INVISIBLE);
        runtimeLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(runtimeLayout);

        ImageView clockIcon = new ImageView(context);
        clockIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_clock, ContextCompat.getColor(context, Theme.iconActiveColor())));
        clockIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        runtimeLayout.addView(clockIcon);

        runtimeText = new TextView(context);
        runtimeText.setMaxLines(1);
        runtimeText.setText(R.string.Loading);
        runtimeText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        runtimeText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        runtimeText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        runtimeText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        runtimeLayout.addView(runtimeText);

//------ORIGINAL LANGUAGE VIEW----------------------------------------------------------------------

        langLayout = new LinearLayout(context);
        langLayout.setOrientation(HORIZONTAL);
        langLayout.setVisibility(INVISIBLE);
        langLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(langLayout);

        ImageView countriesIcon = new ImageView(context);
        countriesIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_earth, ContextCompat.getColor(context, Theme.iconActiveColor())));
        countriesIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START));
        langLayout.addView(countriesIcon);

        originalLanguageText = new TextView(context);
        originalLanguageText.setText("Loading...");
        originalLanguageText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalLanguageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        originalLanguageText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        originalLanguageText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        langLayout.addView(originalLanguageText);

//------FAVORITE AND WATCHLIST BUTTONS-----------------------------------------------------------------------------

        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        topLayout.addView(buttonsLayout);

        favoriteButton = new CheckedButton(context);
        favoriteButton.setVisibility(INVISIBLE);
        favoriteButton.setStyle(CheckedButton.FAVORITE);
        favoriteButton.setOnClickListener(view -> {
            /*if (movieViewListener != null) {
                movieViewListener.onFavoriteButtonClick(view);
            }*/
        });
        favoriteButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(favoriteButton);

        watchingButton = new CheckedButton(context);
        watchingButton.setVisibility(INVISIBLE);
        watchingButton.setStyle(CheckedButton.WATCHING);
        watchingButton.setOnClickListener(view -> {
            /*if (movieViewListener != null) {
                movieViewListener.onWatchingButtonClick(view);
            }*/
        });
        watchingButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(watchingButton);

//------TITLE and TAGLINE---------------------------------------------------------------------------

        titleTaglineLayout = new LinearLayout(context);
        titleTaglineLayout.setOrientation(VERTICAL);
        titleTaglineLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(titleTaglineLayout);

        titleText = new TextView(context);
        titleText.setVisibility(INVISIBLE);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleTaglineLayout.addView(titleText);

        taglineTextView = new TextView(context);
        taglineTextView.setLines(1);
        taglineTextView.setVisibility(INVISIBLE);
        taglineTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        taglineTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        titleTaglineLayout.addView(taglineTextView);

//------OVERVIEW------------------------------------------------------------------------------------

        overviewLayout = new LinearLayout(context);
        overviewLayout.setOrientation(VERTICAL);
        overviewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        overviewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(overviewLayout);

        overviewTextView = new TextView(context);
        //overviewTextView.setMaxLines(5);
        //overviewTextView.setAnimationDuration(450L);
        //overviewTextView.setEllipsize(TextUtils.TruncateAt.END);
        overviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        overviewTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        //overviewTextView.setOnClickListener(v -> {
        //    if (!overviewTextView.isExpanded()) {
        //        overviewTextView.expand();
        //    }
        //});
        overviewTextView.setOnLongClickListener(v -> {
            if (movieViewListener != null) {
                movieViewListener.onOverviewLongClick(v);
                return true;
            }
            return false;
        });
        overviewLayout.addView(overviewTextView);

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
        crewTitle.setText(context.getString(R.string.Crew));
        crewTitle.setGravity(Gravity.CENTER_VERTICAL);
        crewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
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
        trailersView.setVisibility(INVISIBLE);
        trailersView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        trailersView.setOnClickListener(view -> {
            if (movieViewListener != null) {
                movieViewListener.onTrailersSectionClick(view);
            }
        });
        trailersView.setListener(new TrailersSectionView.SectionTrailersListener() {
            @Override
            public void onTrailerClick(View view, String trailerKey) {
                if (movieViewListener != null) {
                    movieViewListener.onTrailerClick(view, trailerKey);
                }
            }

            @Override
            public boolean onTrailerLongClick(View view, String trailerKey) {
                /*if (movieViewListener != null) {
                    movieViewListener.onTrailerLongClick(view, trailerKey);
                    return true;
                }*/
                return false;
            }
        });
        addView(trailersView);

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSectionView(context);
        imagesView.setVisibility(INVISIBLE);
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(imagesView);

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
        //layout.addView(progressBar);

        originalTitleTitle = new TextView(context);
        originalTitleTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitleTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitleTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(originalTitleTitle);

        originalTitleTextView = new TextView(context);
        originalTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(originalTitleTextView);

        contriesTitle = new TextView(context);
        contriesTitle.setText(context.getString(R.string.CountriesTitle));
        contriesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        contriesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(contriesTitle);

        contriesText = new TextView(context);
        contriesText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        contriesText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(contriesText);

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

        budgetText = new TextView(context);
        budgetText.setText(R.string.Loading);
        budgetText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        budgetText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(budgetText);

        revenueTitle = new TextView(context);
        revenueTitle.setText(context.getString(R.string.Revenue));
        revenueTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        revenueTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(revenueTitle);

        revenueText = new TextView(context);
        revenueText.setText(R.string.Loading);
        revenueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        revenueText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(revenueText);

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
        /*genresView.setOnClickListener(v -> {
            if (movieViewListener != null) {
                movieViewListener.onGenresSectionClick(v);
            }
        });*/
        genresView.setListener((view, genre) -> {
            if (movieViewListener != null) {
                movieViewListener.onGenreSelected(view, genre);
            }
        });
        addView(genresView);

//------BELONG COLLECTION---------------------------------------------------------------------------

        collectionView = new BelongCollectionView(context);
        collectionView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        //addView(collectionView);

//------WEB LINKS-----------------------------------------------------------------------------------

        linksLayout = new LinearLayout(context);
        linksLayout.setOrientation(VERTICAL);
        linksLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        linksLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(linksLayout);

        linkTmdbView = new WebpageView(context);
        linkTmdbView.setText(R.string.ViewOnTMDb);
        linkTmdbView.setDivider(true);
        linkTmdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkTmdbView.setOnClickListener(view -> {
            if (movieViewListener != null) {
                movieViewListener.onMovieUrlClick(view, 1);
            }
        });
        linksLayout.addView(linkTmdbView);

        linkImdbView = new WebpageView(context);
        linkImdbView.setText(R.string.ViewOnIMDb);
        linkImdbView.setDivider(true);
        linkImdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkImdbView.setOnClickListener(view -> {
            if (movieViewListener != null) {
                movieViewListener.onMovieUrlClick(view, 2);
            }
        });
        linksLayout.addView(linkImdbView);

        linkHomeView = new WebpageView(context);
        linkHomeView.setText(R.string.ViewHomepage);
        linkHomeView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkHomeView.setOnClickListener(view -> {
            if (movieViewListener != null) {
                movieViewListener.onMovieUrlClick(view, 3);
            }
        });
        linksLayout.addView(linkHomeView);
    }

    public void addPoster(String posterPath) {
        posterImageView.setImageResource(R.drawable.movie_placeholder_old);

        if (posterPath == null || posterPath.isEmpty()) {
            posterImageView.setImageResource(R.drawable.movie_placeholder_old);
            return;
        }

        Picasso.with(getContext())
                .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
                .placeholder(R.drawable.movie_placeholder_old)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(posterImageView);

        phPoster.hide();
        posterImageView.setVisibility(VISIBLE);
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
            titleTaglineLayout.removeView(taglineTextView);
            return;
        }

        taglineTextView.setText(tagline);
        taglineTextView.setVisibility(VISIBLE);
    }

    public void addOverview(String overview) {
        if (overview == null || overview.isEmpty()) {
            removeView(overviewLayout);
            return;
        }

        overviewTextView.setText(overview);
    }

    public void addReleaseDate(String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty()) {
            releaseDateText.setText("-");
            return;
        }

        releaseDateText.setText(releaseDate);
        releaseDateLayout.setVisibility(VISIBLE);
    }

    public void addVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingTextView.setText(String.valueOf(voteAverage));
    }

    public void addVoteCount(int voteCount) {
        voteCountTextView.setText(String.valueOf(voteCount));
        voteCountLayout.setVisibility(VISIBLE);
    }

    public void addOriginalTitle(String title) {
        if (title == null || title.isEmpty()) {
            infoLayout.removeView(originalTitleTitle);
            infoLayout.removeView(originalTitleTextView);
            return;
        }

        originalTitleTextView.setText(title);
    }

    public void addOriginalLanguage(String origLang) {
        if (origLang == null || origLang.isEmpty()) {
            shortInfoLayout.removeView(langLayout);
            return;
        }

        originalLanguageText.setText(origLang);
        langLayout.setVisibility(VISIBLE);
    }

    public void addImages(String poster, String backdrop, int postersCount, int backdropsCount) {
        if (poster == null || poster.isEmpty() || backdrop == null || backdrop.isEmpty()) {
            removeView(imagesView);
            return;
        }

        imagesView.setPoster(poster);
        imagesView.setBackdrop(backdrop);
        imagesView.setPostersCount(postersCount);
        imagesView.setBackdropsCount(backdropsCount);
        imagesView.setVisibility(VISIBLE);
    }

    public void addRuntime(int runtime) {
        if (runtime == 0) {
            shortInfoLayout.removeView(runtimeLayout);
            return;
        }

        runtimeText.setText(AndroidUtils.formatRuntime(runtime));
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

    public void addRevenue(int revenue) {
        if (revenue == 0) {
            infoLayout.removeView(revenueTitle);
            infoLayout.removeView(revenueText);
            return;
        }

        revenueText.setText(AndroidUtils.formatCurrency(revenue));
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
            infoLayout.removeView(contriesTitle);
            infoLayout.removeView(contriesText);
            return;
        }

        contriesText.setText(countries);
    }

    public void addCompanies(String companies) {
        if (companies == null || companies.isEmpty()) {
            infoLayout.removeView(companiesTitle);
            infoLayout.removeView(companiesTextView);
            return;
        }

        companiesTextView.setText(companies);
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

    public void addTrailers(List<Trailer> trailers) {
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

    // GETTERS

    public ImagesSectionView getImagesView() {
        return imagesView;
    }

    public TrailersSectionView getTrailersView() {
        return trailersView;
    }

//--------------------------------------------------------------------------------------------------

    public MovieViewLayout setCrew(List<Crew> crews) {
        if (crews == null) {
            removeView(crewLayout);
            return this;
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

    public MovieViewLayout setGenres(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            removeView(genresView);
        } else {
            genresView.setGenres(genres);
            genresView.getProgressBar().setVisibility(INVISIBLE);
        }

        return this;
    }

    public void addBelongCollection(BelongsToCollection collection) {
        if (collection == null) {
            removeView(collectionView);
            return;
        }

        collectionView.addName(collection.name);
        collectionView.addImage(collection.backdropPath);
    }
}