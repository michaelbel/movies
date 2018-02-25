package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.extensions.AndroidExtensions;
import org.michaelbel.moviemade.ui.interfaces.PersonViewListener;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;
import java.util.Objects;

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

    private ProgressBar progressBar;

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
        profileImage.setVisibility(INVISIBLE);
        profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
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
        birthdayLayout.setVisibility(INVISIBLE);
        birthdayLayout.setOrientation(HORIZONTAL);
        birthdayLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(birthdayLayout);

        ImageView birthdayIcon = new ImageView(context);
        birthdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_cake, ContextCompat.getColor(context, Theme.iconActiveColor())));
        birthdayIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START));
        birthdayLayout.addView(birthdayIcon);

        birthdayText = new TextView(context);
        birthdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        birthdayText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        birthdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        birthdayLayout.addView(birthdayText);

//------DEATHDAY------------------------------------------------------------------------------------

        deathdayLayout = new LinearLayout(context);
        deathdayLayout.setVisibility(INVISIBLE);
        deathdayLayout.setOrientation(HORIZONTAL);
        deathdayLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(deathdayLayout);

        ImageView deathdayIcon = new ImageView(context);
        deathdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        deathdayIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START | Gravity.CENTER_VERTICAL));
        deathdayLayout.addView(deathdayIcon);

        deathdayText = new TextView(context);
        deathdayText.setText(R.string.Loading);
        deathdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        deathdayText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        deathdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        deathdayLayout.addView(deathdayText);

//------BIRTHPLACE----------------------------------------------------------------------------------

        birthPlaceLayout = new LinearLayout(context);
        birthPlaceLayout.setVisibility(INVISIBLE);
        birthPlaceLayout.setOrientation(HORIZONTAL);
        birthPlaceLayout.setOnClickListener(v -> personViewListener.onBirthPlaceClick(v));
        birthPlaceLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(birthPlaceLayout);

        ImageView birthplaceIcon = new ImageView(context);
        birthplaceIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_home, ContextCompat.getColor(context, Theme.iconActiveColor())));
        birthplaceIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START));
        birthPlaceLayout.addView(birthplaceIcon);

        birthPlaceText = new TextView(context);
        birthPlaceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        birthPlaceText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
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
        nameText.setVisibility(INVISIBLE);
        nameText.setTextIsSelectable(AndroidUtils.textSelect());
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
        otherNamesText.setVisibility(INVISIBLE);
        otherNamesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        otherNamesText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        otherNamesText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        nameCareerLayout.addView(otherNamesText);

//------PROGRESS BAR--------------------------------------------------------------------------------

        progressBar = new ProgressBar(context);
        progressBar.setVisibility(VISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        topLayout.addView(progressBar);

//------BIO-----------------------------------------------------------------------------------------

        bioLayout = new LinearLayout(context);
        bioLayout.setOrientation(VERTICAL);
        bioLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        bioLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(bioLayout);

        bioText = new TextView(context);
        bioText.setTextIsSelectable(AndroidUtils.textSelect());
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
        tmdbPageView.setOnClickListener(view -> personViewListener.onWebpageClick(view, 0));
        tmdbPageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        pagesLayout.addView(tmdbPageView);

        imdbPageView = new WebpageView(context);
        imdbPageView.setText(R.string.ViewOnIMDb);
        imdbPageView.setDivider(true);
        imdbPageView.setOnClickListener(view -> personViewListener.onWebpageClick(view, 1));
        imdbPageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        pagesLayout.addView(imdbPageView);

        homePageView = new WebpageView(context);
        homePageView.setText(R.string.ViewHomepage);
        homePageView.setOnClickListener(view -> personViewListener.onWebpageClick(view, 2));
        homePageView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
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
        if (TextUtils.isEmpty(birthday)) {
            birthdayText.setText("-");
            return;
        }

        String text = getContext().getString(R.string.Birthday, AndroidExtensions.formatBirthday(birthday), AndroidExtensions.getAge(birthday));
        int pos = AndroidExtensions.formatBirthday(birthday).length() + 3 + (int)Math.ceil(Math.log10(AndroidExtensions.getAge(birthday))); // 3 is (1 space + 2 braces) and number of digits

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        birthdayText.setText(spannable);
    }

    public void addDeathday(String deathday) {
        if (TextUtils.isEmpty(deathday)) {
            shortInfoLayout.removeView(deathdayLayout);
            return;
        }

        String text = getContext().getString(R.string.Deathday, AndroidExtensions.formatBirthday(deathday));
        int pos = AndroidExtensions.formatBirthday(deathday).length();

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TypefaceSpan("sans-serif-medium"),text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        deathdayText.setText(spannable);
    }

    public void addBirthPlace(CharSequence birthPlace) {
        if (birthPlace == null || birthPlace.toString().isEmpty()) {
            shortInfoLayout.removeView(birthPlaceLayout);
            return;
        }

        String text = getContext().getString(R.string.BirthPlace, birthPlace);
        int pos = birthPlace.length();

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        birthPlaceText.setText(spannable);
    }

    public void addName(String name) {
        if (name == null || name.isEmpty()) {
            nameText.setText("-");
            return;
        }

        nameText.setText(name);
    }

    /*public void addCareer(String careers) {
        if (careers == null || careers.isEmpty()) {
            nameCareerLayout.removeView(careerText);
            return;
        }

        careerText.setText(careers);
    }*/

    public void addOtherNames(String names) {
        if (names == null || names.isEmpty() || Objects.equals(names, "")) {
            Log.e("2580", "OtherNamesField is Empty");
            nameCareerLayout.removeView(otherNamesText);
            return;
        }

        otherNamesText.setText(getContext().getString(R.string.KnownAs, names));
    }

    public void loadedSuccess() {
        progressBar.setVisibility(INVISIBLE);

        profileImage.setVisibility(VISIBLE);
        nameText.setVisibility(VISIBLE);
        otherNamesText.setVisibility(VISIBLE);
        birthdayLayout.setVisibility(VISIBLE);
        deathdayLayout.setVisibility(VISIBLE);
        birthPlaceLayout.setVisibility(VISIBLE);
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