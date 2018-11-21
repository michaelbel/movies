package org.michaelbel.moviemade.ui.modules.settings.cell;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.LayoutHelper;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;

@SuppressLint("ClickableViewAccessibility")
public class TextCell extends FrameLayout {

    public static final int MODE_DEFAULT = 0;
    public static final int MODE_VALUE_TEXT = 1;
    public static final int MODE_SWITCH = 2;
    public static final int MODE_CHECKBOX = 3;
    public static final int MODE_COLOR = 4;
    public static final int MODE_ICON = 5;

    @IntDef({
        MODE_DEFAULT,
        MODE_VALUE_TEXT,
        MODE_SWITCH,
        MODE_CHECKBOX,
        MODE_COLOR,
        MODE_ICON
    })
    private @interface Mode {}

    protected AppCompatTextView textView;
    private AppCompatTextView valueText;
    private AppCompatImageView iconView;
    private SwitchCompat switchCompat;
    private AppCompatCheckBox checkBox;

    private int iconColor;
    private int cellHeight;

    protected Paint paint;
    private boolean divider;
    private Rect rect = new Rect();
    private int currentMode = MODE_DEFAULT;

    public TextCell(Context context) {
        super(context);

        iconColor = R.color.iconActive;
        cellHeight = (int) context.getResources().getDimension(R.dimen.cell_height);

        setForeground(DrawableUtil.INSTANCE.selectableItemBackgroundDrawable(context));
        setBackgroundColor(ContextCompat.getColor(context, R.color.background));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, R.color.divider));
        }

        iconView = new AppCompatImageView(context);
        iconView.setVisibility(INVISIBLE);
        iconView.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(DeviceUtil.INSTANCE.dp(context, 36), DeviceUtil.INSTANCE.dp(context, 36),
                Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 0, 0));
        addView(iconView);

        textView = new AppCompatTextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setTextColor(ContextCompat.getColor(context, R.color.primaryText));
        textView.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 16, 0));
        addView(textView);

        valueText = new AppCompatTextView(context);
        valueText.setLines(1);
        valueText.setMaxLines(1);
        valueText.setSingleLine();
        valueText.setVisibility(INVISIBLE);
        valueText.setEllipsize(TextUtils.TruncateAt.END);
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        valueText.setTextColor(ContextCompat.getColor(context, R.color.secondaryText));
        valueText.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(valueText);

        switchCompat = new SwitchCompat(context);
        switchCompat.setClickable(false);
        switchCompat.setFocusable(false);
        switchCompat.setVisibility(INVISIBLE);
        switchCompat.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(switchCompat);

        checkBox = new AppCompatCheckBox(context);
        checkBox.setClickable(false);
        checkBox.setVisibility(INVISIBLE);
        checkBox.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(checkBox);

        setMode(currentMode);
    }

    public TextCell setIcon(@DrawableRes int icon) {
        iconView.setVisibility(icon == 0 ? INVISIBLE : VISIBLE);
        if (icon != 0) {
            iconView.setImageDrawable(DrawableUtil.INSTANCE.getIcon(getContext(), icon, ContextCompat.getColor(getContext(), iconColor)));
        }

        if (currentMode == MODE_ICON) {
            textView.setLayoutParams(LayoutHelper.INSTANCE.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 56, 0, 16, 0));
        }

        return this;
    }

    public TextCell setText(@NonNull String text) {
        textView.setText(text);
        return this;
    }

    public TextCell setText(@StringRes int textId) {
        textView.setText(getContext().getText(textId));
        return this;
    }

    public TextCell setChecked(boolean value) {
        if (currentMode == MODE_SWITCH) {
            switchCompat.setChecked(value);
        }

        if (currentMode == MODE_CHECKBOX) {
            checkBox.setChecked(value);
        }

        return this;
    }

    public TextCell setValue(@NonNull String value) {
        valueText.setText(value);
        return this;
    }

    public TextCell setValue(@StringRes int textId) {
        valueText.setText(getContext().getText(textId));
        return this;
    }

    public TextCell setMode(@Mode int mode) {
        currentMode = mode;

        if (currentMode == MODE_DEFAULT) {
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_VALUE_TEXT) {
            valueText.setVisibility(VISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_SWITCH) {
            switchCompat.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_CHECKBOX) {
            checkBox.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_COLOR) {
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
            iconView.setVisibility(INVISIBLE);
        } else if (currentMode == MODE_ICON) {
            iconView.setVisibility(VISIBLE);
            valueText.setVisibility(INVISIBLE);
            switchCompat.setVisibility(INVISIBLE);
            checkBox.setVisibility(INVISIBLE);
        }

        return this;
    }

    public TextCell setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    public TextCell setHeight(int height) {
        cellHeight = height;
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = cellHeight + (divider ? 1 : 0);
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