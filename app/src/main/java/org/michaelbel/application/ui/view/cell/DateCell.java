package org.michaelbel.application.ui.view.cell;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.annotation.Beta;
import org.michaelbel.application.util.ScreenUtils;

@Beta
public class DateCell extends FrameLayout {

    private TextView textView;

    public DateCell(Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_200));

        textView = new TextView(context);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 16, 0));
        addView(textView);
    }

    public void setText(@NonNull String text) {
        textView.setText(text.toUpperCase());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = ScreenUtils.dp(32);

        setMeasuredDimension(width, height);
    }
}