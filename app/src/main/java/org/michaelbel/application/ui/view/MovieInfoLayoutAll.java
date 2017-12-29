package org.michaelbel.application.ui.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.model.Crew;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.trailer.TrailerView;
import org.michaelbel.application.util.ScreenUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import at.blogc.android.views.ExpandableTextView;

public class MovieInfoLayoutAll extends LinearLayout {

    private ImageView posterImageView;

    private RatingView ratingView;
    private TextView ratingTextView;
    private TextView voteCountTextView;

    private TextView dateTextView;

    private TextView runtimeTextView;

    private TextView countriesTextView;

    private CheckedButton favButton;

    private LinearLayout titleTaglineLayout;
    private TextView titleTextView;
    private TextView taglineTextView;

    private LinearLayout overviewLayout;
    private ExpandableTextView overviewTextView;

    private TrailersSectionView trailersView;
    private TrailersAdapter trailersAdapter;
    private List<Trailer> trailersList = new ArrayList<>();

    private ImagesSectionView imagesView;

    private LinearLayout crewLayout;
    private TextView directorsTitleTextView;
    private TextView directorsTextView;
    private TextView writersTitleTextView;
    private TextView writersTextView;
    private TextView producersTitleTextView;
    private TextView producersTextView;

    private TextView originalTitleTextView;
    private TextView originalLangTextView;
    private TextView statusTextView;
    private TextView budgetTextView;
    private TextView revenueTextView;
    private TextView genresTextView;
    private TextView companiesTextView;

    private LinearLayout linksLayout;
    private MoviePageView linkTmdbView;
    private MoviePageView linkImdbView;
    private MoviePageView linkHomeView;

    private Callback callback;
    private InfoMovieListener infoMovieListener;

    public interface InfoMovieListener {
        boolean onOverviewLongClick(View view);

        void onTrailersSectionClick(View view);
        void onTrailerClick(View view, String trailerKey);
        boolean onTrailerLongClick(View view, String trailerKey);

        void onMovieUrlClick(View view, int position);

        void onFavButtonClick(View view);
    }

    public interface Callback {
        void onMovieLoaded();
    }

    public MovieInfoLayoutAll(Context context) {
        super(context);
        initialize(context);
    }

    public MovieInfoLayoutAll(Context context, AttributeSet attrs) {
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
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(110, 180,
                Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(posterImageView);

//--------------------------------------------------------------------------------------------------

        LinearLayout layout1 = new LinearLayout(context);
        layout1.setOrientation(VERTICAL);
        layout1.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                GravityCompat.START | Gravity.TOP, 110 + 32, 16, 16, 0));
        topLayout.addView(layout1);

//------RATING VIEW---------------------------------------------------------------------------------

        LinearLayout layout0 = new LinearLayout(context);
        layout0.setOrientation(HORIZONTAL);
        layout1.addView(layout0);

        ratingView = new RatingView(context);
        ratingView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL));
        layout0.addView(ratingView);

        ratingTextView = new TextView(context);
        ratingTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        ratingTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        ratingTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        ratingTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(ratingTextView);

        LinearLayout layout6 = new LinearLayout(context);
        layout6.setOrientation(HORIZONTAL);
        layout6.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        layout0.addView(layout6);

        voteCountTextView = new TextView(context);
        voteCountTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        voteCountTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        voteCountTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        voteCountTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.BOTTOM));
        layout6.addView(voteCountTextView);

        ImageView voteCountIcon = new ImageView(context);
        voteCountIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_account_multiple,
                ContextCompat.getColor(context, Theme.iconActiveColor())));
        voteCountIcon.setLayoutParams(LayoutHelper.makeLinear(12, 12,
                Gravity.START | Gravity.BOTTOM, 2, 0, 0, 1));
        layout6.addView(voteCountIcon);

//------DATE VIEW-----------------------------------------------------------------------------------

        LinearLayout layout2 = new LinearLayout(context);
        layout2.setOrientation(HORIZONTAL);
        layout2.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(layout2);

        ImageView dateIcon = new ImageView(context);
        dateIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar,
                ContextCompat.getColor(context, Theme.iconActiveColor())));
        dateIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL));
        layout2.addView(dateIcon);

        dateTextView = new TextView(context);
        dateTextView.setMaxLines(1);
        dateTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        dateTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        dateTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        layout2.addView(dateTextView);

//------RUNTIME VIEW--------------------------------------------------------------------------------

        LinearLayout layout3 = new LinearLayout(context);
        layout3.setOrientation(HORIZONTAL);
        layout3.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(layout3);

        ImageView clockIcon = new ImageView(context);
        clockIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_clock,
                ContextCompat.getColor(context, Theme.iconActiveColor())));
        clockIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL));
        layout3.addView(clockIcon);

        runtimeTextView = new TextView(context);
        runtimeTextView.setMaxLines(1);
        runtimeTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        runtimeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        runtimeTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        runtimeTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        layout3.addView(runtimeTextView);

//------COUNTRY VIEW--------------------------------------------------------------------------------

        LinearLayout layout4 = new LinearLayout(context);
        layout4.setOrientation(HORIZONTAL);
        layout4.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                GravityCompat.START, 0, 12, 0, 0));
        layout1.addView(layout4);

        ImageView countriesIcon = new ImageView(context);
        countriesIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_earth,
                ContextCompat.getColor(context, Theme.iconActiveColor())));
        countriesIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START));
        layout4.addView(countriesIcon);

        countriesTextView = new TextView(context);
        countriesTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        countriesTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        countriesTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        countriesTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        layout4.addView(countriesTextView);

//------FAVORITE BUTTON-----------------------------------------------------------------------------

        favButton = new CheckedButton(context);
        favButton.setOnClickListener(v -> {
            if (infoMovieListener != null) {
                infoMovieListener.onFavButtonClick(v);
            }
        });
        favButton.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        topLayout.addView(favButton);

//------TITLE and TAGLINE---------------------------------------------------------------------------

        titleTaglineLayout = new LinearLayout(context);
        titleTaglineLayout.setOrientation(VERTICAL);
        titleTaglineLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(titleTaglineLayout);

        titleTextView = new TextView(context);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        titleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleTaglineLayout.addView(titleTextView);

        taglineTextView = new TextView(context);
        taglineTextView.setLines(1);
        taglineTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        taglineTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        titleTaglineLayout.addView(taglineTextView);

//------OVERVIEW------------------------------------------------------------------------------------

        overviewLayout = new LinearLayout(context);
        overviewLayout.setOrientation(VERTICAL);
        overviewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        overviewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(overviewLayout);

        overviewTextView = new ExpandableTextView(context);
        overviewTextView.setMaxLines(5);
        overviewTextView.setAnimationDuration(450L);
        overviewTextView.setEllipsize(TextUtils.TruncateAt.END);
        overviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        overviewTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        overviewTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        overviewTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 16));
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

//------TRAILERS SECTION VIEW-----------------------------------------------------------------------

        trailersAdapter = new TrailersAdapter();

        trailersView = new TrailersSectionView(context);
        trailersView.setPadding(0, 0, 0, ScreenUtils.dp(4));
        trailersView.setAdapter(trailersAdapter);
        trailersView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        trailersView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onTrailersSectionClick(view);
            }
        });
        trailersView.setListener(new TrailersSectionView.SectionTrailersListener() {
            @Override
            public void onTrailerClick(View view, int position) {
                if (infoMovieListener != null) {
                    Trailer trailer = trailersList.get(position);
                    infoMovieListener.onTrailerClick(view, trailer.key);
                }
            }

            @Override
            public boolean onTrailerLongClick(View view, int position) {
                if (infoMovieListener != null) {
                    Trailer trailer = trailersList.get(position);
                    infoMovieListener.onTrailerLongClick(view, trailer.key);
                    return true;
                }
                return false;
            }
        });
        addView(trailersView);

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSectionView(context);
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        //addView(imagesView);

//------CREW VIEW--------------------------------------------------------------------------------------------

        crewLayout = new LinearLayout(context);
        crewLayout.setOrientation(VERTICAL);
        crewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        crewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(crewLayout);

        TextView crewTitle = new TextView(context);
        crewTitle.setLines(1);
        crewTitle.setMaxLines(1);
        crewTitle.setSingleLine();
        crewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        crewTitle.setText(context.getString(R.string.Crew));
        crewTitle.setGravity(Gravity.CENTER_VERTICAL);
        crewTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        crewTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        crewTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48,
                16, 0, 16, 0));
        crewLayout.addView(crewTitle);

        directorsTitleTextView = new TextView(context);
        directorsTitleTextView.setText(context.getString(R.string.Directors));
        directorsTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        directorsTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        crewLayout.addView(directorsTitleTextView);

        directorsTextView = new TextView(context);
        directorsTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        directorsTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(directorsTextView);

        writersTitleTextView = new TextView(context);
        writersTitleTextView.setText(context.getString(R.string.Writers));
        writersTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        writersTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(writersTitleTextView);

        writersTextView = new TextView(context);
        writersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        writersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(writersTextView);

        producersTitleTextView = new TextView(context);
        producersTitleTextView.setText(context.getString(R.string.Producers));
        producersTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        producersTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        crewLayout.addView(producersTitleTextView);

        producersTextView = new TextView(context);
        producersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        producersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(producersTextView);

//------INFO VIEW--------------------------------------------------------------------------------------------

        LinearLayout infoLayout = new LinearLayout(context);
        infoLayout.setOrientation(VERTICAL);
        infoLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        infoLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(infoLayout);

        TextView infoTitle = new TextView(context);
        infoTitle.setLines(1);
        infoTitle.setMaxLines(1);
        infoTitle.setSingleLine();
        infoTitle.setText(context.getString(R.string.Info));
        infoTitle.setGravity(Gravity.CENTER_VERTICAL);
        infoTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        infoTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        infoTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        infoTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48,
                16, 0, 16, 0));
        infoLayout.addView(infoTitle);

        TextView originalTitle = new TextView(context);
        originalTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalTitle);

        originalTitleTextView = new TextView(context);
        originalTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalTitleTextView);

        TextView originalLang = new TextView(context);
        originalLang.setText(context.getString(R.string.OriginalLang));
        originalLang.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalLang.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(originalLang);

        originalLangTextView = new TextView(context);
        originalLangTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalLangTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalLangTextView);

        TextView statusTitle = new TextView(context);
        statusTitle.setText(context.getString(R.string.Status));
        statusTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        statusTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(statusTitle);

        statusTextView = new TextView(context);
        statusTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        statusTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(statusTextView);

        TextView budgetTitle = new TextView(context);
        budgetTitle.setText(context.getString(R.string.Budget));
        budgetTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        budgetTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(budgetTitle);

        budgetTextView = new TextView(context);
        budgetTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        budgetTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(budgetTextView);

        TextView revenueTitle = new TextView(context);
        revenueTitle.setText(context.getString(R.string.Revenue));
        revenueTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        revenueTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(revenueTitle);

        revenueTextView = new TextView(context);
        revenueTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        revenueTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(revenueTextView);

//--------------------------------------------------------------------------------------------------

        TextView genresTitle = new TextView(context);
        genresTitle.setText(context.getString(R.string.Genres));
        genresTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        genresTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(genresTitle);

        genresTextView = new TextView(context);
        genresTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        genresTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(genresTextView);

//--------------------------------------------------------------------------------------------------

        TextView companiesTitle = new TextView(context);
        companiesTitle.setText(context.getString(R.string.ProductionCompanies));
        companiesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        companiesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(companiesTitle);

        companiesTextView = new TextView(context);
        companiesTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        companiesTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, 16, 0, 16, 12));
        infoLayout.addView(companiesTextView);

//------WEB LINKS--------------------------------------------------------------------------------------------

        linksLayout = new LinearLayout(context);
        linksLayout.setOrientation(VERTICAL);
        linksLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        linksLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 6));
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

    public MovieInfoLayoutAll setPoster(@NonNull String posterPath) {
        SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        String size = prefs.getString("image_quality_poster", "w342");

        Picasso.with(getContext())
               .load(Url.getImage(posterPath, size))
               .placeholder(R.drawable.movie_placeholder)
               .into(posterImageView);
        return this;
    }

    /*public MovieInfoLayoutAll setFavButton(int movieId) {
        DatabaseHelper database = DatabaseHelper.getInstance(getContext());
        boolean isExist = database.isMovieExist(movieId);

        if (isExist) {
            favButton.setIcon(R.drawable.ic_heart);
            favButton.setText(R.string.Remove);
        } else {
            favButton.setIcon(R.drawable.ic_heart_outline);
            favButton.setText(R.string.Add);
        }

        database.close();
        return this;
    }
*/
    public MovieInfoLayoutAll setTitle(@NonNull String movieTitle) {
        titleTextView.setText(movieTitle.isEmpty() ? "-" : movieTitle);
        return this;
    }

    public MovieInfoLayoutAll setGenres(@NonNull List<Genre> genresList) {
        StringBuilder text = new StringBuilder();
        for (Genre genre : genresList) {
            text.append(genre.name);
            if (genre != genresList.get(genresList.size() - 1)) {
                text.append(", ");
            }
        }

        genresTextView.setText(genresList.isEmpty() ? "-" : text);
        return this;
    }

    public MovieInfoLayoutAll setDate(@NonNull String releaseDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

        Date date = null;
        try {
            date = format.parse(releaseDate);
        } catch (ParseException e) {
            //FirebaseCrash.report(e);
        }

        dateTextView.setText(releaseDate.isEmpty() ? "-" : newFormat.format(date));
        return this;
    }

    public MovieInfoLayoutAll setRuntime(int runTime) {
        SimpleDateFormat formatMin = new SimpleDateFormat("m", Locale.getDefault());
        SimpleDateFormat formatHours = new SimpleDateFormat("H:m", Locale.getDefault());

        Date date;
        String str = null;

        try {
            date = formatMin.parse(String.valueOf(runTime));
            str = formatHours.format(date);
        } catch (ParseException e) {
            //FirebaseCrash.report(e);
        }

        runtimeTextView.setText(runTime == 0 ? "-" : getContext().getString(R.string.RuntimeMin, runTime) + " / " + str);
        return this;
    }

    public MovieInfoLayoutAll setCountries(@NonNull List<Movie.Countries> countriesList) {
        StringBuilder text = new StringBuilder();
        for (Movie.Countries country : countriesList) {
            if (country.name.equals("United States of America")) {
                country.name = "USA";
            } else if (country.name.equals("United Kingdom")) {
                country.name = "UK";
            }

            text.append(country.name);
            if (country != countriesList.get(countriesList.size() - 1)) {
                text.append(", ");
            }
        }

        countriesTextView.setText(countriesList.isEmpty() ? "-" : text);
        return this;
    }

    public MovieInfoLayoutAll setStatus(@NonNull String status) {
        statusTextView.setText(status.isEmpty() ? "-" : status);
        return this;
    }

    public MovieInfoLayoutAll setTagline(@NonNull String tagline) {
        if (tagline.isEmpty()) {
            titleTaglineLayout.removeView(taglineTextView);
        } else {
            taglineTextView.setText(tagline);
        }
        return this;
    }

    public MovieInfoLayoutAll setBudget(int budget) {
        if (budget == 0) {
            // delete views
            budgetTextView.setText("-");
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
            budgetTextView.setText("$ " + formatter.format(budget));
        }
        return this;
    }

    public MovieInfoLayoutAll setRevenue(int revenue) {
        if (revenue == 0) {
            // delete views
            revenueTextView.setText("-");
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
            revenueTextView.setText("$ " + formatter.format(revenue));
        }
        return this;
    }

    public MovieInfoLayoutAll setOriginalTitle(@NonNull String originalTitle) {
        originalTitleTextView.setText(originalTitle.isEmpty() ? "-" : originalTitle);
        return this;
    }

    public MovieInfoLayoutAll setOriginalLang(@Nullable String originalLang) {
        if (originalLang != null) {
            originalLangTextView.setText(originalLang.isEmpty() ? "-" : originalLang);
        } else {
            originalLangTextView.setText("-");
        }

        return this;
    }

    public MovieInfoLayoutAll setCompanies(@NonNull List<Movie.Companies> companiesList) {
        StringBuilder text = new StringBuilder();
        for (Movie.Companies company : companiesList) {
            text.append(company.name);
            if (company != companiesList.get(companiesList.size() - 1)) {
                text.append(", ");
            }
        }

        companiesTextView.setText(companiesList.isEmpty() ? "-" : text);
        return this;
    }

    public MovieInfoLayoutAll setVoteAverage(float voteAverage) {
        ratingView.setRating(voteAverage);
        ratingTextView.setText(String.valueOf(voteAverage));
        return this;
    }

    public MovieInfoLayoutAll setVoteCount(int voteCount) {
        voteCountTextView.setText(String.valueOf(voteCount));
        return this;
    }

    public MovieInfoLayoutAll setOverview (@NonNull String overview) {
        if (overview.isEmpty()) {
            removeView(overviewLayout);
        } else {
            overviewTextView.setText(overview);
        }
        return this;
    }

    public MovieInfoLayoutAll setHomePage(@NonNull String homePage) {
        if (homePage.isEmpty()) {
            linkImdbView.setDivider(false);
            linksLayout.removeView(linkHomeView);
        }
        return this;
    }

    public MovieInfoLayoutAll setCrew(@NonNull List<Crew> crewList) {
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
            crewLayout.removeView(directorsTitleTextView);
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
            crewLayout.removeView(writersTitleTextView);
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
            crewLayout.removeView(producersTitleTextView);
            crewLayout.removeView(producersTextView);
        }

        if (text1.toString().isEmpty() && text2.toString().isEmpty() && text3.toString().isEmpty()) {
            removeView(crewLayout);
        }
        return this;
    }

    public MovieInfoLayoutAll setTrailers(@NonNull List<Trailer> list) {
        if (list.isEmpty()) {
            removeView(trailersView);
        } else {
            trailersList.addAll(list);
            trailersAdapter.notifyDataSetChanged();
        }

        if (callback != null) {
            callback.onMovieLoaded();
        }

        return this;
    }

    public MovieInfoLayoutAll setImages(@NonNull String poster, @NonNull String backdrop, int postersCount, int backdropsCount) {
        imagesView.setPoster(poster);
        imagesView.setBackdrop(backdrop);
        imagesView.setPostersCount(postersCount);
        imagesView.setBackdropsCount(backdropsCount);
        return this;
    }

    public void setListener(@NonNull InfoMovieListener listener) {
        infoMovieListener = listener;
    }

    public void setCallback(Callback listener) {
        callback = listener;
    }

    public class TrailersAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TrailerView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailersList.get(position);

            TrailerView view = (TrailerView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key);

            if (position == 0) {
                view.changeLayoutParams(true);
            }

            if (position == trailersList.size() - 1) {
                view.changeLayoutParams(false);
            }
        }

        @Override
        public int getItemCount() {
            return trailersList != null ? trailersList.size() : 0;
        }
    }
}