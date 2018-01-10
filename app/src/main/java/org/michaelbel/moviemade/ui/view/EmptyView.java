package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;

@SuppressWarnings("all")
public class EmptyView extends LinearLayout {

    public static final int MODE_NO_CONNECTION = 0;
    public static final int MODE_NO_MOVIES = 1;
    public static final int MODE_NO_PEOPLE = 2;
    public static final int MODE_NO_REVIEWS = 3;
    public static final int MODE_NO_RESULTS = 4;
    public static final int MODE_NO_HISTORY = 5;
    public static final int MODE_NO_TRAILERS = 6;

    @IntDef({
            MODE_NO_CONNECTION,
            MODE_NO_MOVIES,
            MODE_NO_PEOPLE,
            MODE_NO_REVIEWS,
            MODE_NO_RESULTS,
            MODE_NO_HISTORY,
            MODE_NO_TRAILERS,
    })
    public @interface Mode {}

    private ImageView emptyIcon;
    private TextView emptyText;

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

        emptyIcon = new ImageView(getContext());
        emptyIcon.setLayoutParams(LayoutHelper.makeLinear(56, 56));
        addView(emptyIcon);

        emptyText = new TextView(getContext());
        emptyText.setGravity(Gravity.CENTER);
        emptyText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        emptyText.setTextColor(ContextCompat.getColor(getContext(), Theme.iconActiveColor()));
        emptyText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 10, 24, 0));
        addView(emptyText);
    }

    public EmptyView setMode(@Mode int mode) {
        if (mode == MODE_NO_CONNECTION) {
            setIcon(R.drawable.ic_offline);
            setText(R.string.NoConnection);
        } else if (mode == MODE_NO_MOVIES) {
            setIcon(R.drawable.ic_movieroll);
            setText(R.string.NoMovies);
        } else if (mode == MODE_NO_PEOPLE) {
            setIcon(R.drawable.ic_account_circle);
            setText(R.string.NoPeople);
        } else if (mode == MODE_NO_REVIEWS) {
            setIcon(R.drawable.ic_review); // change
            setText(R.string.NoReviews);
        } else if (mode == MODE_NO_RESULTS) {
            setIcon(R.drawable.ic_database_search);
            setText(R.string.NoResults);
        } else if (mode == MODE_NO_HISTORY) {
            setIcon(R.drawable.ic_search_history);
            setText(R.string.SearchHistoryEmpty);
        } else if (mode == MODE_NO_TRAILERS) {
            setIcon(R.drawable.ic_trailer);
            setText(R.string.NoTrailers);
        }

        return this;
    }

    private void setIcon(int icon) {
        emptyIcon.setImageDrawable(Theme.getIcon(icon, ContextCompat.getColor(getContext(), Theme.iconActiveColor())));
    }

    private void setText(@StringRes int textId) {
        emptyText.setText(getContext().getString(textId));
    }
}