package org.michaelbel.moviemade.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.core.widget.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.utils.ScreenUtils;

public class AboutView extends LinearLayout {

    private TextView appNameText;
    private TextView versionText;

    public AboutView(Context context) {
        super(context);

        setOrientation(VERTICAL);
        setPadding(ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24), ScreenUtils.dp(24));

        ImageView launcherIcon = new ImageView(context);
        launcherIcon.setImageResource(R.mipmap.ic_launcher);
        launcherIcon.setLayoutParams(LayoutHelper.makeLinear(125, 125, Gravity.CENTER_HORIZONTAL));
        addView(launcherIcon);

        appNameText = new TextView(context);
        appNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        appNameText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        appNameText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        appNameText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 5, 0, 0));
        addView(appNameText);

        versionText = new TextView(context);
        versionText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        versionText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        versionText.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL, 0, 4, 0, 0));
        addView(versionText);
    }

    public void setName(String text) {
        appNameText.setText(text);
    }

    public void setVersion(String versionName, int versionCode, String versionDate) {
        versionText.setText(getContext().getString(R.string.VersionBuildDate, versionName, versionCode, versionDate));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
        int height = getMeasuredHeight();
        setMeasuredDimension(width, height);
    }
}