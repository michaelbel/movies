package org.michaelbel.moviemade.ui.modules.movie.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.material.widget.LayoutHelper;
import org.michaelbel.moviemade.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class BackdropView extends FrameLayout {

    public CardView labelView;

    private TextView textView;
    private ImageView backdropImage;

    public BackdropView(@NonNull Context context) {
        super(context);
        initialize(context);
    }

    public BackdropView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(@NonNull Context context) {
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary));

        backdropImage = new ImageView(context);
        backdropImage.setScaleType(ImageView.ScaleType.FIT_XY);
        backdropImage.setLayoutParams(LayoutHelper.makeFrame(context, LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(backdropImage);

        ImageView playIcon = new ImageView(context);
        playIcon.setScaleType(ImageView.ScaleType.FIT_XY);
        playIcon.setImageDrawable(Extensions.getIcon(context, R.drawable.ic_play_circle, ContextCompat.getColor(context, R.color.transparent80)));
        playIcon.setLayoutParams(LayoutHelper.makeFrame(context, 52, 52, Gravity.CENTER));
        //addView(playIcon);

//------Show Status Label---------------------------------------------------------------------------

        labelView = new CardView(context);
        labelView.setCardElevation(0);
        labelView.setUseCompatPadding(false);
        labelView.setPreventCornerOverlap(false);
        labelView.setRadius(Extensions.dp(context, 5));
        labelView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.transparent50));
        labelView.setLayoutParams(LayoutHelper.makeFrame(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.BOTTOM, 4, 0, 16 + 16 + 56, 4));
        //addView(labelView);

        textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.loading_status);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setTextColor(ContextCompat.getColor(context, R.color.white));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 5, 2.5F, 5, 2.5F));
        //labelView.addView(textView);
    }

    public void setImage(String path) {
        Glide.with(getContext())
             .load("http://image.tmdb.org/t/p/original/" + path)
             .thumbnail(0.1F)
             .into(backdropImage);
    }

    public void setLabel(String text) {
        textView.setText(text);
    }
}