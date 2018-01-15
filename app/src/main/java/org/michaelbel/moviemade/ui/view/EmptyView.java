package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
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
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;

public class EmptyView extends LinearLayout {

    private TextView emptyText;
    private ImageView emptyIcon;

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
        emptyText.setTextColor(ContextCompat.getColor(getContext(), Theme.iconActiveColor()));
        emptyText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        emptyText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 10, 24, 0));
        addView(emptyText);
    }

    public EmptyView setMode(@EmptyViewMode int mode) {
        if (mode == EmptyViewMode.MODE_NO_CONNECTION) {
            setIcon(R.drawable.ic_offline);
            setText(R.string.NoConnection);
        } else if (mode == EmptyViewMode.MODE_NO_MOVIES) {
            setIcon(R.drawable.ic_movieroll);
            setText(R.string.NoMovies);
        } else if (mode == EmptyViewMode.MODE_NO_PEOPLE) {
            setIcon(R.drawable.ic_account_circle);
            setText(R.string.NoPeople);
        } else if (mode == EmptyViewMode.MODE_NO_REVIEWS) {
            setIcon(R.drawable.ic_review); // change
            setText(R.string.NoReviews);
        } else if (mode == EmptyViewMode.MODE_NO_RESULTS) {
            setIcon(R.drawable.ic_database_search);
            setText(R.string.NoResults);
        } else if (mode == EmptyViewMode.MODE_NO_HISTORY) {
            setIcon(R.drawable.ic_search_history);
            setText(R.string.SearchHistoryEmpty);
        } else if (mode == EmptyViewMode.MODE_NO_TRAILERS) {
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