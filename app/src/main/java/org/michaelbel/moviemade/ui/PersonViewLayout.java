package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
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

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.AndroidExtensions;
import org.michaelbel.moviemade.utils.LayoutHelper;

import java.util.Objects;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

public class PersonViewLayout extends LinearLayout {

    private ImageView profileImage;
    private FrameLayout profileImageEmpty;

    private LinearLayout nameCareerLayout;
    private TextView nameText;
    private TextView careerText;
    private TextView otherNamesText;
    private LinearLayout bioLayout;
    private TextView bioText;
    private LinearLayout shortInfoLayout;
    private TextView popularityText;
    private LinearLayout birthdayLayout;
    private TextView birthdayText;
    private LinearLayout deathdayLayout;
    private TextView deathdayText;
    private LinearLayout birthPlaceLayout;
    private TextView birthPlaceText;
    private LinearLayout pagesLayout;
    private ProgressBar progressBar;

    public PersonViewLayout(Context context) {
        super(context);

        setOrientation(VERTICAL);

        FrameLayout topLayout = new FrameLayout(context);
        topLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        addView(topLayout);

//------PROFILE IMAGE-------------------------------------------------------------------------------

        profileImage = new ImageView(context);
        profileImage.setVisibility(INVISIBLE);
        profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
        profileImage.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        topLayout.addView(profileImage);

        profileImageEmpty = new FrameLayout(context);
        profileImageEmpty.setLayoutParams(LayoutHelper.makeFrame(120, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 0));
        profileImageEmpty.setVisibility(INVISIBLE);
        topLayout.addView(profileImageEmpty);

//--------------------------------------------------------------------------------------------------

        shortInfoLayout = new LinearLayout(context);
        shortInfoLayout.setOrientation(VERTICAL);
        shortInfoLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 120 + 32, 16, 16, 16));
        topLayout.addView(shortInfoLayout);

//------POPULARITY----------------------------------------------------------------------------------

        LinearLayout popularityLayout = new LinearLayout(context);
        popularityLayout.setOrientation(HORIZONTAL);
        popularityLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 12, 0, 0, 0));
        //infoLayout.addView(popularityLayout);

        popularityText = new TextView(context);
        popularityText.setMaxLines(1);
        popularityText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
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
        //birthdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_cake, ContextCompat.getColor(context, R.color.iconActive)));
        birthdayIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START));
        birthdayLayout.addView(birthdayIcon);

        birthdayText = new TextView(context);
        birthdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //birthdayText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        birthdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        birthdayLayout.addView(birthdayText);

//------DEATHDAY------------------------------------------------------------------------------------

        deathdayLayout = new LinearLayout(context);
        deathdayLayout.setVisibility(INVISIBLE);
        deathdayLayout.setOrientation(HORIZONTAL);
        deathdayLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(deathdayLayout);

        ImageView deathdayIcon = new ImageView(context);
        //deathdayIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_calendar, ContextCompat.getColor(context, Theme.iconActiveColor())));
        deathdayIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START | Gravity.CENTER_VERTICAL));
        deathdayLayout.addView(deathdayIcon);

        deathdayText = new TextView(context);
        deathdayText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //deathdayText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        deathdayText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        deathdayLayout.addView(deathdayText);

//------BIRTHPLACE----------------------------------------------------------------------------------

        birthPlaceLayout = new LinearLayout(context);
        birthPlaceLayout.setVisibility(INVISIBLE);
        birthPlaceLayout.setOrientation(HORIZONTAL);
        birthPlaceLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START, 0, 12, 0, 0));
        shortInfoLayout.addView(birthPlaceLayout);

        ImageView birthplaceIcon = new ImageView(context);
        //birthplaceIcon.setImageDrawable(Theme.getIcon(R.drawable.ic_home, ContextCompat.getColor(context, R.color.iconActive)));
        birthplaceIcon.setLayoutParams(LayoutHelper.makeLinear(20, 20, Gravity.START));
        birthPlaceLayout.addView(birthplaceIcon);

        birthPlaceText = new TextView(context);
        birthPlaceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        //birthPlaceText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        birthPlaceText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL,6, 0, 0, 0));
        birthPlaceLayout.addView(birthPlaceText);

//------NAME and OTHER NAMES and CAREER-------------------------------------------------------------

        nameCareerLayout = new LinearLayout(context);
        nameCareerLayout.setOrientation(VERTICAL);
        nameCareerLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, GravityCompat.START | Gravity.TOP, 16, 202, 16, 12));
        topLayout.addView(nameCareerLayout);

        nameText = new TextView(context);
        nameText.setVisibility(INVISIBLE);
        nameText.setTextIsSelectable(true);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        nameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameCareerLayout.addView(nameText);

        careerText = new TextView(context);
        careerText.setLines(1);
        careerText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        //nameCareerLayout.addView(careerText);

        otherNamesText = new TextView(context);
        otherNamesText.setVisibility(INVISIBLE);
        otherNamesText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        otherNamesText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        nameCareerLayout.addView(otherNamesText);

//------PROGRESS BAR--------------------------------------------------------------------------------

        progressBar = new ProgressBar(context);
        progressBar.setVisibility(VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.accent), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        topLayout.addView(progressBar);

//------BIO-----------------------------------------------------------------------------------------

        bioLayout = new LinearLayout(context);
        bioLayout.setOrientation(VERTICAL);
        bioLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 0));
        addView(bioLayout);

        bioText = new TextView(context);
        bioText.setTextIsSelectable(true);
        bioText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        bioText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        bioText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        bioLayout.addView(bioText);

//------PAGES---------------------------------------------------------------------------------------

        pagesLayout = new LinearLayout(context);
        pagesLayout.setOrientation(VERTICAL);
        pagesLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(pagesLayout);

    }

    public void setDates(String birthDay, String deathDay) {
        if (TextUtils.isEmpty(birthDay) && TextUtils.isEmpty(deathDay)) {
            shortInfoLayout.removeView(deathdayLayout);
            birthdayText.setText("-");
            return;
        }

        if (TextUtils.isEmpty(birthDay)) {
            birthdayText.setText("-");
            return;
        }

        SpannableStringBuilder spannable;

        if (TextUtils.isEmpty(deathDay)) {
            shortInfoLayout.removeView(deathdayLayout);

            int age = AndroidExtensions.getAge(birthDay);
            String dateOfBirth = AndroidExtensions.formatBirthday(birthDay);

            String text = getContext().getString(R.string.BirthdayWithOld, dateOfBirth, age); // Date of Birth + Age.
            int pos = dateOfBirth.length() + 3 + (int) Math.ceil(Math.log10(age)); // 3 is (1 space + 2 braces) and number of digits

            spannable = new SpannableStringBuilder(text);
            spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.primaryText)), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            String dateOfBirth = AndroidExtensions.formatBirthday(birthDay);

            String text = getContext().getString(R.string.Birthday, dateOfBirth); // Date of Birth without age.
            int pos = dateOfBirth.length();

            spannable = new SpannableStringBuilder(text);
            spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.primaryText)), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        birthdayText.setText(spannable);

        if (!TextUtils.isEmpty(deathDay)) {
            int age = AndroidExtensions.getAgeDeath(birthDay, deathDay);
            String dateOfDeath = AndroidExtensions.formatBirthday(deathDay);

            String text = getContext().getString(R.string.Deathday, dateOfDeath, age); // Date of Death + Age.
            int pos = dateOfDeath.length() + 3 + (int) Math.ceil(Math.log10(age)); // 3 is (1 space + 2 braces) and number of digits

            SpannableStringBuilder spannable2 = new SpannableStringBuilder(text);
            spannable2.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.primaryText)),text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable2.setSpan(new TypefaceSpan("sans-serif-medium"),text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            deathdayText.setText(spannable2);
        }
    }

    /*@Deprecated
    public void addBirthday(String birthDay) {
        if (TextUtils.isEmpty(birthDay)) {
            birthdayText.setText("-");
            return;
        }

        SpannableStringBuilder spannable;

        if (deathdayText.getText().toString().isEmpty()) {
            int age = AndroidExtensions.getAge(birthDay);
            String dateOfBirth = AndroidExtensions.formatBirthday(birthDay);

            String text = getContext().getString(R.string.BirthdayWithOld, dateOfBirth, age); // Date of Birth + Age.
            int pos = dateOfBirth.length() + 3 + (int) Math.ceil(Math.log10(age)); // 3 is (1 space + 2 braces) and number of digits

            spannable = new SpannableStringBuilder(text);
            spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            String dateOfBirth = AndroidExtensions.formatBirthday(birthDay);

            String text = getContext().getString(R.string.Birthday, dateOfBirth); // Date of Birth without age.
            int pos = dateOfBirth.length();

            spannable = new SpannableStringBuilder(text);
            spannable.setSpan(new TypefaceSpan("sans-serif-medium"), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        birthdayText.setText(spannable);
    }*/

    /*@Deprecated
    public void addDeathday(String deathDay) {
        if (TextUtils.isEmpty(deathDay)) {
            infoLayout.removeView(deathdayLayout);
            return;
        }

        int age = AndroidExtensions.getAge(deathDay);
        String dateOfDeath = AndroidExtensions.formatBirthday(deathDay);

        String text = getContext().getString(R.string.Deathday, dateOfDeath, age); // Date of Death + Age.
        int pos = dateOfDeath.length() + 3 + (int) Math.ceil(Math.log10(age)); // 3 is (1 space + 2 braces) and number of digits

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), Theme.primaryTextColor())),text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TypefaceSpan("sans-serif-medium"),text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        deathdayText.setText(spannable);
    }*/

    public void addBirthPlace(CharSequence birthPlace) {
        if (birthPlace == null || birthPlace.toString().isEmpty()) {
            shortInfoLayout.removeView(birthPlaceLayout);
            return;
        }

        String text = getContext().getString(R.string.BirthPlace, birthPlace);
        int pos = birthPlace.length();

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.primaryText)), text.length() - pos, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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
}