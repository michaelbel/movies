package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.ui.interfaces.PersonViewListener;

import java.util.Locale;

public class PersonViewLayout extends LinearLayout {

    private ImageView profileImage;

    private LinearLayout nameCareerLayout;
    private TextView nameText;
    private TextView careerText;

    private TextView otherNamesText;

    private LinearLayout bioLayout;
    private TextView bioText;

    private CheckedButton favoriteButton;

    private LinearLayout shortInfoLayout;

    private TextView popularityText;

    private LinearLayout birthdayLayout;
    private TextView birthdayText;

    private LinearLayout deathdayLayout;
    private TextView deathdayText;

    private LinearLayout birthPlaceLayout;
    private TextView birthPlaceText;

    private LinearLayout pagesLayout;
    private WebpageView tmdbPageView;
    private WebpageView imdbPageView;
    private WebpageView homePageView;

    private PersonViewListener personViewListener;

    public PersonViewLayout(Context context) {
        super(context);

        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));

        FrameLayout topLayout = new FrameLayout(context);
        topLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        topLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(topLayout);

//------PROFILE IMAGE-------------------------------------------------------------------------------

        profileImage = new ImageView(context);
        profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
        //profileImage.setImageResource(R.drawable.movi_placeholder_old);
        profileImage.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(profileImage);

//--------------------------------------------------------------------------------------------------

        shortInfoLayout = new LinearLayout(context);
        shortInfoLayout.setOrientation(VERTICAL);
        shortInfoLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 120 + 32, 16, 16, 16));
        topLayout.addView(shortInfoLayout);

//------POPULARITY----------------------------------------------------------------------------------

        LinearLayout popularityLayout = new LinearLayout(context);
        popularityLayout.setOrientation(HORIZONTAL);
        popularityLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        //shortInfoLayout.addView(popularityLayout);

        popularityText = new TextView(context);
        popularityText.setMaxLines(1);
        popularityText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        popularityText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        popularityText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        popularityText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        popularityLayout.addView(popularityText);

//------BIRTHDAY------------------------------------------------------------------------------------

        birthdayLayout = new LinearLayout(context);
        birthdayLayout.setOrientation(HORIZONTAL);
        birthdayLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(birthdayLayout);

        ImageView birthdayIcon = new ImageView(context);
        birthdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_cake, ContextCompat.getColor(context, Theme.iconActiveColor())));
        birthdayIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        birthdayLayout.addView(birthdayIcon);

        birthdayText = new TextView(context);
        birthdayText.setMaxLines(1);
        birthdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        birthdayText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        birthdayText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        birthdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        birthdayLayout.addView(birthdayText);

//------DEATHDAY------------------------------------------------------------------------------------

        deathdayLayout = new LinearLayout(context);
        deathdayLayout.setOrientation(HORIZONTAL);
        deathdayLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(deathdayLayout);

        ImageView deathdayIcon = new ImageView(context);
        deathdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        deathdayIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        deathdayLayout.addView(deathdayIcon);

        deathdayText = new TextView(context);
        deathdayText.setMaxLines(1);
        deathdayText.setText(R.string.Loading);
        deathdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        deathdayText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        deathdayText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        deathdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        deathdayLayout.addView(deathdayText);

//------BIRTHPLACE----------------------------------------------------------------------------------

        birthPlaceLayout = new LinearLayout(context);
        birthPlaceLayout.setOrientation(HORIZONTAL);
        birthPlaceLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(birthPlaceLayout);

        ImageView birthplaceIcon = new ImageView(context);
        birthplaceIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_home, ContextCompat.getColor(context, Theme.iconActiveColor())));
        birthplaceIcon.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START));
        birthPlaceLayout.addView(birthplaceIcon);

        birthPlaceText = new TextView(context);
        birthPlaceText.setText("Loading...");
        birthPlaceText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        birthPlaceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        birthPlaceText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        birthPlaceText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        birthPlaceLayout.addView(birthPlaceText);

//------FAVORITE BUTTON-----------------------------------------------------------------------------

        LinearLayout buttonsLayout = new LinearLayout(context);
        buttonsLayout.setOrientation(HORIZONTAL);
        buttonsLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP | Gravity.END, 0, 16 + 180 - 36 - 4, 16, 0));
        //topLayout.addView(buttonsLayout);

        favoriteButton = new CheckedButton(context);
        favoriteButton.setStyle(CheckedButton.FAVORITE);
        favoriteButton.setOnClickListener(view -> {

        });
        favoriteButton.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        buttonsLayout.addView(favoriteButton);

//------NAME and OTHER NAMES and CAREER-------------------------------------------------------------

        nameCareerLayout = new LinearLayout(context);
        nameCareerLayout.setOrientation(VERTICAL);
        nameCareerLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(nameCareerLayout);

        nameText = new TextView(context);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        nameText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        nameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameCareerLayout.addView(nameText);

        careerText = new TextView(context);
        careerText.setLines(1);
        careerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        careerText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        //nameCareerLayout.addView(careerText);

        otherNamesText = new TextView(context);
        otherNamesText.setLines(1);
        otherNamesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        otherNamesText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        otherNamesText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, 0, 8, 0, 0));
        nameCareerLayout.addView(otherNamesText);

//------BIO-----------------------------------------------------------------------------------------

        bioLayout = new LinearLayout(context);
        bioLayout.setOrientation(VERTICAL);
        bioLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        bioLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(bioLayout);

        bioText = new TextView(context);
        bioText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        bioText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        bioText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        bioText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        bioLayout.addView(bioText);

//------PAGES---------------------------------------------------------------------------------------

        pagesLayout = new LinearLayout(context);
        pagesLayout.setOrientation(VERTICAL);
        pagesLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        pagesLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(pagesLayout);

        tmdbPageView = new WebpageView(context);
        tmdbPageView.setText(R.string.ViewOnTMDb);
        tmdbPageView.setDivider(true);
        tmdbPageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        tmdbPageView.setOnClickListener(view -> {
            if (personViewListener != null) {
                personViewListener.onWebpageClick(view, 0);
            }
        });
        pagesLayout.addView(tmdbPageView);

        imdbPageView = new WebpageView(context);
        imdbPageView.setText(R.string.ViewOnIMDb);
        imdbPageView.setDivider(true);
        imdbPageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        imdbPageView.setOnClickListener(view -> {
            if (personViewListener != null) {
                personViewListener.onWebpageClick(view, 1);
            }
        });
        pagesLayout.addView(imdbPageView);

        homePageView = new WebpageView(context);
        homePageView.setText(R.string.ViewHomepage);
        homePageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        homePageView.setOnClickListener(view -> {
            if (personViewListener != null) {
                personViewListener.onWebpageClick(view, 2);
            }
        });
        pagesLayout.addView(homePageView);
    }

    public void addProfileImage(String profilePath) {
        if (profilePath == null || profilePath.isEmpty()) {
            profileImage.setImageResource(R.drawable.people_placeholder_old);
            return;
        }

        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, "w500", profilePath))
               //.placeholder(R.drawable.movie_placeholder_old)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(profileImage);
    }

    public void addPopularity(String popularity) {
        if (popularity == null || popularity.isEmpty()) {
            return;
        }

        popularityText.setText(popularity);
    }

    public void addBirthday(String birthday) {
        if (birthday == null || birthday.isEmpty()) {
            birthdayText.setText("-");
            return;
        }

        birthdayText.setText(birthday);
    }

    public void addDeathday(String deathday) {
        if (deathday == null || deathday.isEmpty()) {
            shortInfoLayout.removeView(deathdayLayout);
            return;
        }

        deathdayText.setText(deathday);
    }

    public void addBirthPlace(String birthPlace) {
        if (birthPlace == null || birthPlace.isEmpty()) {
            shortInfoLayout.removeView(birthPlaceLayout);
            return;
        }

        birthPlaceText.setText(birthPlace);
    }

    public void addName(String name) {
        if (name == null || name.isEmpty()) {
            nameText.setText("-");
            return;
        }

        nameText.setText(name);
    }

    public void addCareer(String careers) {
        if (careers == null || careers.isEmpty()) {
            nameCareerLayout.removeView(careerText);
            return;
        }

        careerText.setText(careers);
    }

    public void addOtherNames(String names) {
        if (names == null || names.isEmpty()) {
            nameCareerLayout.removeView(otherNamesText);
            return;
        }

        otherNamesText.setText(names);
    }

    public void addBio(String bio) {
        if (bio == null || bio.isEmpty()) {
            removeView(bioLayout);
            return;
        }

        bioText.setText(bio);
    }

    public void addImdb(String url) {
        if (url == null || url.isEmpty()) {
            pagesLayout.removeView(imdbPageView);
            tmdbPageView.setDivider(false);
        }
    }

    public void addHomepage(String url) {
        if (url == null || url.isEmpty()) {
            pagesLayout.removeView(homePageView);
            imdbPageView.setDivider(false);
        }
    }

    public void addListener(PersonViewListener listener) {
        personViewListener = listener;
    }

    @Deprecated
    public void favoriteButtonVisibility(int visibility) {
        favoriteButton.setVisibility(visibility);
    }

    @Deprecated
    public void setFavoriteButton(boolean favorite) {
        favoriteButton.setChecked(favorite);
    }
}