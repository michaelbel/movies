package org.michaelbel.application.ui.view;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;

@SuppressWarnings("all")
public class EmptyView extends LinearLayout {

    private TextView emptyText;
    private ImageView emptyImage;

    public EmptyView(Context context) {
        super(context);
        initialize();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        emptyImage = new ImageView(getContext());
        emptyImage.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        addView(emptyImage);

        emptyText = new TextView(getContext());
        emptyText.setGravity(Gravity.CENTER);
        emptyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        emptyText.setTextColor(ContextCompat.getColor(getContext(), Theme.secondaryTextColor()));
        emptyText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 16, 24, 0));
        addView(emptyText);
    }

    public EmptyView setText(@StringRes int textId) {
        emptyText.setText(getContext().getText(textId));
        return this;
    }

    public EmptyView setImage(int resId) {
        emptyImage.setImageResource(resId);
        return this;
    }
}