package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.moviemade.LayoutHelper;

@SuppressWarnings("all")
public class TitleView extends FrameLayout {

    public TextView titleTextView;
    public TextView subtitleTextView;

    public TitleView(Context context) {
        super(context);
        initialize(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context context) {
        titleTextView = new TextView(context);
        titleTextView.setSingleLine();
        titleTextView.setTextColor(0xFFFFFFFF);
        titleTextView.setEllipsize(TextUtils.TruncateAt.END);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        titleTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        titleTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, Gravity.TOP, 0, 8, 0, 0));
        addView(titleTextView);

        subtitleTextView = new TextView(context);
        subtitleTextView.setSingleLine();
        subtitleTextView.setTextColor(0xFFD5E8F7);
        subtitleTextView.setEllipsize(TextUtils.TruncateAt.END);
        subtitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        subtitleTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM, 0, 0, 0, 8));
        addView(subtitleTextView);
    }

    public void setTitle(@NonNull CharSequence title) {
        titleTextView.setText(title);
    }

    public void setTitle(@StringRes int textId) {
        setTitle(getContext().getText(textId));
    }

    public void setSubtitle(@NonNull CharSequence subtitle) {
        subtitleTextView.setText(subtitle);
    }

    public void setSubtitle(@StringRes int textId) {
        setSubtitle(getContext().getText(textId));
    }
}