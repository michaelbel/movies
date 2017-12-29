package org.michaelbel.application.ui.view;

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

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.ui.view.widget.MaskImageView;
import org.michaelbel.application.util.ScreenUtils;

@SuppressWarnings("all")
public class CastView extends FrameLayout {

    public TextView nameTextView;
    public TextView characterTextView;
    public MaskImageView profileImageView;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public CastView(Context context) {
        super(context);

        setForeground(Theme.selectableItemBackgroundDrawable());
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        profileImageView = new MaskImageView(context);
        profileImageView.setShapeDrawable(MaskImageView.CIRCLE);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER);
        profileImageView.setLayoutParams(LayoutHelper.makeFrame(62, 62, Gravity.START, 8, 8, 0, 8));
        addView(profileImageView);

        nameTextView = new TextView(context);
        nameTextView.setLines(1);
        nameTextView.setMaxLines(1);
        nameTextView.setSingleLine(true);
        nameTextView.setEllipsize(TextUtils.TruncateAt.END);
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        nameTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        nameTextView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        nameTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 88, 18, 16, 0));
        addView(nameTextView);

        characterTextView = new TextView(context);
        characterTextView.setLines(1);
        characterTextView.setMaxLines(1);
        characterTextView.setSingleLine(true);
        characterTextView.setEllipsize(TextUtils.TruncateAt.END);
        characterTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        characterTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        characterTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        characterTextView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM, 88, 0, 16, 18));
        addView(characterTextView);
    }

    public CastView setProfileImage(@NonNull String profilePath) {
        // todo Get real sizes
        //SharedPreferences prefs = getContext().getSharedPreferences("mainconfig", Context.MODE_PRIVATE);
        //String size = prefs.getString("image_quality_profile", "w185");
        String size = "w185";

        Picasso.with(getContext())
               .load(Url.getImage(profilePath, size))
               .placeholder(R.drawable.people_placeholder)
               .into(profileImageView);

        return this;
    }

    public CastView setName(@NonNull String name) {
        nameTextView.setText(name);
        return this;
    }

    public CastView setCharacter(@NonNull String character) {
        characterTextView.setText(character);
        return this;
    }

    public CastView setDivider(boolean divider) {
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