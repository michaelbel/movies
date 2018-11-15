package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureImageView;

import org.michaelbel.moviemade.data.constants.CreditsKt;
import org.michaelbel.moviemade.utils.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.Theme;
import org.michaelbel.moviemade.data.dao.Crew;
import org.michaelbel.moviemade.data.dao.Image;
import org.michaelbel.moviemade.utils.AndroidExtensions;
import org.michaelbel.moviemade.ui.modules.movie.views.RatingView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.core.content.ContextCompat;

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

    private LinearLayout titleTaglineLayout;

    private TextView titleText;
    private TextView taglineText;

    private LinearLayout overviewLayout;


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

    public MovieViewLayout(Context context) {
        super(context);

//------FAVORITE AND WATCHLIST BUTTONS--------------------------------------------------------------

        buttonsLayout = new LinearLayout(context);
        buttonsLayout.setVisibility(INVISIBLE);
        buttonsLayout.setOrientation(HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        //topLayout.addView(buttonsLayout);


//------OVERVIEW------------------------------------------------------------------------------------

        overviewLayout = new LinearLayout(context);
        overviewLayout.setOrientation(VERTICAL);
        overviewLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
        overviewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(overviewLayout);

        /*if (AndroidUtils.fullOverview()) {
            overviewText.setMaxLines(1000);
            overviewText.setOnClickListener(null);
        }*/

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



//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSection(context);
        imagesView.setVisibility(INVISIBLE);
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

    public void addImages(List<Image> posters, List<Image> backdrops, int postersSize, int backdropsSize) {
        if (posters == null || posters.isEmpty() || backdrops == null || backdrops.isEmpty()) {
            removeView(imagesView);
            return;
        }

        /*imagesView.addPosters(posters, postersSize);
        imagesView.addBackdrops(backdrops, backdropsSize);*/
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

    public void addCountries(String countries) {
        if (countries == null || countries.isEmpty()) {
            infoLayout.removeView(countriesTitle);
            infoLayout.removeView(countriesText);
            return;
        }

        countriesText.setText(countries);
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
            switch (crew.getDepartment()) {
                case CreditsKt.DIRECTING:
                    directors.add(crew.getName());
                    break;
                case CreditsKt.WRITING:
                    writers.add(crew.getName());
                    break;
                case CreditsKt.PRODUCTION:
                    producers.add(crew.getName());
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


}