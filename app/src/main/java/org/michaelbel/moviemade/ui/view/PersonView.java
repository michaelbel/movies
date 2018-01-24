package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.ui.view.widget.MaskImageView;
import org.michaelbel.moviemade.util.ScreenUtils;

import java.util.Locale;

public class PersonView extends FrameLayout {

    public TextView nameText;
    public TextView characterText;
    public MaskImageView profileImage;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public PersonView(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        profileImage = new MaskImageView(context);
        profileImage.setShapeDrawable(MaskImageView.CIRCLE);
        profileImage.setScaleType(ImageView.ScaleType.CENTER);
        profileImage.setLayoutParams(LayoutHelper.makeFrame(62, 62, Gravity.START, 8, 8, 0, 8));
        addView(profileImage);

        nameText = new TextView(context);
        nameText.setLines(1);
        nameText.setMaxLines(1);
        nameText.setSingleLine(true);
        nameText.setEllipsize(TextUtils.TruncateAt.END);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        nameText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        nameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 88, 18, 16, 0));
        addView(nameText);

        characterText = new TextView(context);
        characterText.setLines(1);
        characterText.setMaxLines(1);
        characterText.setSingleLine(true);
        characterText.setEllipsize(TextUtils.TruncateAt.END);
        characterText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        characterText.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        characterText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        characterText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM, 88, 0, 16, 18));
        addView(characterText);
    }

    public PersonView setProfile(@NonNull String profilePath) {
        Picasso.with(getContext())
               .load(String.format(Locale.US, Url.TMDB_IMAGE, "w185", profilePath))
               .placeholder(R.drawable.people_placeholder_old)
               .into(profileImage);

        return this;
    }

    public PersonView setName(@NonNull String name) {
        nameText.setText(name);
        return this;
    }

    public PersonView setCharacter(@NonNull String character) {
        characterText.setText(character);
        return this;
    }

    public PersonView setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(ScreenUtils.dp(88), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
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