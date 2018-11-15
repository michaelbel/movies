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

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.utils.ConstantsKt;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.LayoutHelper;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

@Deprecated
public class BackdropView extends FrameLayout {

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
        backdropImage.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        addView(backdropImage);
    }

    public void setImage(String path) {
        Glide.with(getContext()).load(String.format(Locale.US, ConstantsKt.TMDB_IMAGE, "original", path)).thumbnail(0.1F).into(backdropImage);
    }
}