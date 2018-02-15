package org.michaelbel.moviemade.ui.view.cell;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class EmptyCell extends FrameLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_TEXT = 1;
    public static final int MODE_LOADING = 2;

    @IntDef({
            MODE_DEFAULT,
            MODE_TEXT,
            MODE_LOADING
    })
    private @interface Mode {}

    private int mHeight = 8;
    private int currentMode = MODE_DEFAULT;

    private TextView textView;
    private ProgressBar progressBar;

    public EmptyCell(Context context) {
        super(context);

        textView = new TextView(context);
        textView.setVisibility(INVISIBLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        textView.setPadding(ScreenUtils.dp(16), ScreenUtils.dp(12), ScreenUtils.dp(16), ScreenUtils.dp(12));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START));
        addView(textView);

        progressBar = new ProgressBar(context);
        progressBar.setVisibility(INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        addView(progressBar);

        setMode(MODE_DEFAULT);
    }

    public EmptyCell setHeight(int height) {
        if (currentMode == MODE_DEFAULT) {
            mHeight = height;
            requestLayout();
        }

        return this;
    }

    public EmptyCell setText(CharSequence text) {
        if (currentMode == MODE_TEXT) {
            textView.setText(text);
        }

        return this;
    }

    public EmptyCell setText(@StringRes int textId) {
        setText(getContext().getText(textId));
        return this;
    }

    public EmptyCell setMode(@Mode int mode) {
        currentMode = mode;

        if (currentMode == MODE_DEFAULT) {
            textView.setVisibility(GONE);
            progressBar.setVisibility(GONE);
        } else if (currentMode == MODE_TEXT) {
            textView.setVisibility(VISIBLE);
            progressBar.setVisibility(GONE);
        } else if (currentMode == MODE_LOADING) {
            progressBar.setVisibility(VISIBLE);
            textView.setVisibility(GONE);
        }

        return this;
    }

    public void setTextGravity(int gravity) {
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, gravity));
    }

    public EmptyCell changeLayoutParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (ScreenUtils.isLandscape()) {
            params.leftMargin = ScreenUtils.dp(56);
            params.rightMargin = ScreenUtils.dp(56);
        }

        setLayoutParams(params);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
        int height = mHeight;

        if (currentMode == MODE_TEXT) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
        } else if (currentMode == MODE_LOADING) {
            height = MeasureSpec.makeMeasureSpec(ScreenUtils.dp(54), MeasureSpec.EXACTLY);
        } else if (currentMode == MODE_DEFAULT) {
            height = mHeight;
        }

        setMeasuredDimension(width, height);
    }
}