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

import com.alexvasilkov.gestures.views.GestureImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.rest.model.Crew;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.rest.model.v3.Keyword;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.interfaces.MovieViewListener;
import org.michaelbel.moviemade.ui.view.section.CompaniesSection;
import org.michaelbel.moviemade.ui.view.section.GenresSection;
import org.michaelbel.moviemade.ui.view.section.ImagesSection;
import org.michaelbel.moviemade.ui.view.section.KeywordsSection;
import org.michaelbel.moviemade.ui.view.section.TrailersSection;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieViewLayout extends LinearLayout {

    private PlaceholderView posterPh;
    private GestureImageView posterImage;

    private LinearLayout shortInfoLayout;

    private RatingView ratingView;
    private TextView ratingTextView;

    private LinearLayout voteCountLayout;
    private TextView voteCountText;

    private PlaceholderView releaseDatePh;
    private LinearLayout releaseDateLayout;
    private TextView releaseDateText;

    private PlaceholderView runtimePh;
    private LinearLayout runtimeLayout;
    private TextView runtimeText;

    private PlaceholderView langPh;
    private LinearLayout langLayout;
    private TextView originalLanguageText;

    private CheckedButton favoriteButton;
    private CheckedButton watchingButton;

    private LinearLayout titleTaglineLayout;

    private PlaceholderView titlePh;
    private TextView titleText;

    private PlaceholderView taglinePh;
    private TextView taglineTextView;

    private LinearLayout overviewLayout;
    private TextView overviewTextView;

    private TrailersSection trailersView;

    private ImagesSection imagesView;

    private LinearLayout crewLayout;
    private ProgressBar crewProgressBar;

    private TextView directorsTitle;
    private TextView directorsText;

    private TextView writersTitle;
    private TextView writersTextView;

    private TextView producersTitle;
    private TextView producersTextView;

    private LinearLayout infoLayout;
    //private ProgressBar progressBar;

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

        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));

        FrameLayout topLayout = new FrameLayout(context);
        topLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        topLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(topLayout);

//------POSTER IMAGE--------------------------------------------------------------------------------

        posterPh = new PlaceholderView(context);
        posterPh.show();
        posterPh.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(posterPh);

        posterImage = new GestureImageView(context);
        posterImage.setVisibility(INVISIBLE);
        posterImage.setScaleType(ImageView.ScaleType.FIT_XY);
        posterImage.setImageResource(R.drawable.movie_placeholder_old);
        posterImage.setOnClickListener(view -> movieViewListener.onPosterClick(view));
        posterImage.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(posterImage);

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

        voteCountText = new TextView(context);
        voteCountText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        voteCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        voteCountText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM));
        voteCountLayout.addView(voteCountText);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple, ContextCompat.getColor(context, Theme.primaryTextColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(12, 12, Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        voteCountLayout.addView(voteCountIcon);

//------DATE VIEW-----------------------------------------------------------------------------------

        releaseDatePh = new PlaceholderView(context);
        releaseDatePh.showColor();
        releaseDatePh.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(releaseDatePh);

        releaseDateLayout = new LinearLayout(context);
        releaseDateLayout.setOrientation(HORIZONTAL);
        releaseDateLayout.setVisibility(INVISIBLE);
        releaseDateLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(releaseDateLayout);

        ImageView dateIcon = new ImageView(context);
        dateIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        dateIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START | Gravity.CENTER_VERTICAL));
        releaseDateLayout.addView(dateIcon);

        releaseDateText = new TextView(context);
        releaseDateText.setMaxLines(1);
        //releaseDateText.setText("Loading...");
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
        clockIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START | Gravity.CENTER_VERTICAL));
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
        countriesIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START));
        langLayout.addView(countriesIcon);

        originalLanguageText = new TextView(context);
        originalLanguageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        originalLanguageText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
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
        favoriteButton.setOnClickListener(view -> movieViewListener.onFavoriteButtonClick(view));
        favoriteButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(favoriteButton);

        watchingButton = new CheckedButton(context);
        watchingButton.setVisibility(INVISIBLE);
        watchingButton.setStyle(CheckedButton.WATCHING);
        watchingButton.setOnClickListener(view -> movieViewListener.onWatchingButtonClick(view));
        watchingButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(watchingButton);

//------TITLE and TAGLINE---------------------------------------------------------------------------

        titleTaglineLayout = new LinearLayout(context);
        titleTaglineLayout.setOrientation(VERTICAL);
        titleTaglineLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(titleTaglineLayout);

        titlePh = new PlaceholderView(context);
        titlePh.showColor();
        titlePh.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        titleTaglineLayout.addView(titlePh);

        titleText = new TextView(context);
        titleText.setVisibility(INVISIBLE);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        //titleTaglineLayout.addView(titleText);
        titlePh.addView(titleText);

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
            movieViewListener.onOverviewLongClick(v);
            return true;
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

        directorsText = new TextView(context);
        directorsText.setText(R.string.Loading);
        directorsText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        directorsText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        crewLayout.addView(directorsText);

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
        addView(trailersView);

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSection(context);
        imagesView.setVisibility(INVISIBLE);
        imagesView.getPosterViewPager().setOnClickListener(view -> movieViewListener.onPostersClick(view));
        imagesView.getBackdropViewPager().setOnClickListener(view -> movieViewListener.onBackdropsClick(view));
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(imagesView);

//------INFO VIEW-----------------------------------------------------------------------------------

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

        //progressBar = new ProgressBar(context);
        //progressBar.setLayoutParams(LayoutHelper.makeFrame(24, 24, Gravity.END | Gravity.CENTER_VERTICAL));
        //layout.addView(progressBar);

        originalTitle = new TextView(context);
        originalTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(originalTitle);

        originalTitleText = new TextView(context);
        originalTitleText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 16));
        infoLayout.addView(originalTitleText);

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

        countriesTitle = new TextView(context);
        countriesTitle.setText(context.getString(R.string.CountriesTitle));
        countriesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        countriesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        infoLayout.addView(countriesTitle);

        countriesText = new TextView(context);
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
        keywordsView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        keywordsView.setOnClickListener(view -> movieViewListener.onKeywordsSectionClick(view));
        keywordsView.setListener((view, keyword) -> movieViewListener.onKeywordClick(view, keyword));
        addView(keywordsView);

//------COLLECTION VIEW-----------------------------------------------------------------------------

        collectionView = new CollectionView(context);
        collectionView.setOnClickListener(view -> movieViewListener.onCollectionClick(view));
        collectionView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 6));
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

        addView(collectionView);
    }

    public void addPoster(String posterPath) {
        posterImage.setImageResource(R.drawable.movie_placeholder_old);

        if (posterPath == null || posterPath.isEmpty()) {
            posterImage.setImageResource(R.drawable.movie_placeholder_old);
            return;
        }

        Picasso.with(getContext())
                .load(String.format(Locale.US, Url.TMDB_IMAGE, AndroidUtils.posterSize(), posterPath))
                .placeholder(R.drawable.movie_placeholder_old)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(posterImage);

        posterPh.hide();
        posterImage.setVisibility(VISIBLE);
    }

    public void addTitle(String title) {
        if (title == null || title.isEmpty()) {
            titleText.setText("-");
            return;
        }

        titleText.setText(title);
        titleText.setVisibility(VISIBLE);
        titlePh.hideColor();
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
        releaseDatePh.hideColor();
    }

    public void addVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingTextView.setText(String.valueOf(voteAverage));
    }

    public void addVoteCount(int voteCount) {
        voteCountText.setText(String.valueOf(voteCount));
        voteCountLayout.setVisibility(VISIBLE);
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

        runtimeText.setText(AndroidUtils.formatRuntime(runtime));
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
        } else {
            crewProgressBar.setVisibility(INVISIBLE);
        }
    }

    public void addCollection(Collection collection) {
        if (collection == null) {
            removeView(collectionView);
            return;
        }

        collectionView.addName(getContext().getString(R.string.PartOfCollection, collection.name));
        collectionView.addImage(collection.backdropPath);
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