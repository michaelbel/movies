package org.michaelbel.moviemade.ui.view.cell;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.util.ScreenUtils;

@SuppressWarnings("all")
public class TextDetailCell extends FrameLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_SWITCH = 1;
    public static final int MODE_CHECKBOX = 2;

    @IntDef({
            MODE_DEFAULT,
            MODE_SWITCH,
            MODE_CHECKBOX
    })
    private @interface Mode {}

    protected TextView textView;
    protected TextView valueText;
    private SwitchCompat switchCompat;
    private AppCompatCheckBox checkBox;

    private Paint paint;
    private boolean divider;
    private boolean multiline;
    private Rect rect = new Rect();
    private int currentMode = MODE_DEFAULT;

    public TextDetailCell(Context context) {
        super(context);

        setElevation(ScreenUtils.dp(1));
        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 10, 16, 0));
        addView(textView);

        valueText = new TextView(context);
        valueText.setLines(1);
        valueText.setMaxLines(1);
        valueText.setSingleLine();
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        valueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        valueText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 35, 16, 0));
        addView(valueText);

        switchCompat = new SwitchCompat(context);
        switchCompat.setClickable(false);
        switchCompat.setFocusable(false);
        switchCompat.setVisibility(INVISIBLE);
        switchCompat.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(switchCompat);

        checkBox = new AppCompatCheckBox(context);
        checkBox.setClickable(false);
        checkBox.setFocusable(false);
        checkBox.setVisibility(INVISIBLE);
        checkBox.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(checkBox);

        changeSwitchTheme();
        setMode(currentMode);
    }

    public TextDetailCell setText(@NonNull String text) {
        textView.setText(text);
        return this;
    }

    public TextDetailCell setText(@StringRes int textId) {
        textView.setText(getContext().getText(textId));
        return this;
    }

    public TextDetailCell setValue(@NonNull String text) {
        valueText.setText(text);
        return this;
    }

    public TextDetailCell setValue(@StringRes int textId) {
        valueText.setText(getContext().getText(textId));
        return this;
    }

    public TextDetailCell setChecked(boolean value) {
        if (currentMode == MODE_SWITCH) {
            switchCompat.setChecked(value);
        } else if (currentMode == MODE_CHECKBOX) {
            checkBox.setChecked(value);
        }
        return this;
    }


    public TextDetailCell setMode(int mode) {
        currentMode = mode;

        if (currentMode == MODE_DEFAULT) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_SWITCH) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(VISIBLE);
            checkBox.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_CHECKBOX) {
            valueText.setVisibility(VISIBLE);
            checkBox.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
        }

        return this;
    }

    public TextDetailCell setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    /*public void setMultiline(boolean value) {
        multiline = value;

        if (value) {
            valueText.setLines(0);
            valueText.setMaxLines(0);
            valueText.setSingleLine(false);
            valueText.setPadding(0, 0, 0, ScreenUtils.dp(12));
        } else {
            valueText.setLines(1);
            valueText.setMaxLines(1);
            valueText.setSingleLine();
            valueText.setPadding(0, 0, 0, 0);
        }
    }*/

    public TextDetailCell changeLayoutParams() {
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );

        if (ScreenUtils.isLandscape()) {
            params.leftMargin = ScreenUtils.dp(56);
            params.rightMargin = ScreenUtils.dp(56);
        }

        setLayoutParams(params);
        return this;
    }

    private void changeSwitchTheme() {
        int thumbOn = ContextCompat.getColor(getContext(), Theme.thumbOnColor());
        int thumbOff = ContextCompat.getColor(getContext(), Theme.thumbOffColor());

        int trackOn = ContextCompat.getColor(getContext(), Theme.trackOnColor());
        int trackOff = ContextCompat.getColor(getContext(), Theme.trackOffColor());

        DrawableCompat.setTintList(switchCompat.getThumbDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_checked },
                        new int[]{}
                },
                new int[]{
                        thumbOn,
                        thumbOff
                }));

        DrawableCompat.setTintList(switchCompat.getTrackDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{ android.R.attr.state_checked  },
                        new int[]{}
                },
                new int[]{
                        trackOn,
                        trackOff
                }));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height;
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);

        if (multiline) {
            height = getMeasuredHeight();
        } else {
            height = ScreenUtils.dp(64) + (divider ? 1 : 0);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}