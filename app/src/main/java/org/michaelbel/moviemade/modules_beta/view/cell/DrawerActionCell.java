package org.michaelbel.moviemade.modules_beta.view.cell;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.extensions.DeviceUtil;

import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

public class DrawerActionCell extends FrameLayout {

    private TextView textView;
    private ImageView imageView;
    private Rect rect = new Rect();

    public DrawerActionCell(Context context) {
        super(context);

        setForeground(Extensions.selectableItemBackgroundDrawable(context));

        imageView = new ImageView(context);
        imageView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 16, 0, 0, 0));
        addView(imageView);

        textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 72, 0, 16, 0));
        addView(textView);
    }

    public void setIcon(Drawable resId) {
        imageView.setImageDrawable(resId);
    }

    public void setText(@StringRes int textId) {
        textView.setText(getContext().getText(textId));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = DeviceUtil.INSTANCE.dp(getContext(),48);
        setMeasuredDimension(width, height);
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