package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;

@SuppressWarnings("all")
public class TitleView extends FrameLayout {

    public TextView titleText;
    public TextView subtitleText;

    public TitleView(Context context) {
        super(context);
        initialize(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context context) {
        titleText = new TextView(context);
        titleText.setSingleLine();
        titleText.setEllipsize(TextUtils.TruncateAt.END);
        titleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        titleText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.TOP, 0, 8, 0, 0));
        addView(titleText);

        subtitleText = new TextView(context);
        subtitleText.setSingleLine();
        //subtitleText.setTextColor(0xFFD5E8F7);
        subtitleText.setEllipsize(TextUtils.TruncateAt.END);
        subtitleText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        subtitleText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        subtitleText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM, 0, 0, 0, 8));
        addView(subtitleText);
    }

    public void setTitle(@NonNull CharSequence title) {
        titleText.setText(title);
    }

    public void setTitle(@StringRes int textId) {
        setTitle(getContext().getText(textId));
    }

    public void setSubtitle(@NonNull CharSequence subtitle) {
        subtitleText.setText(subtitle);
    }

    public void setSubtitle(@StringRes int textId) {
        setSubtitle(getContext().getText(textId));
    }
}