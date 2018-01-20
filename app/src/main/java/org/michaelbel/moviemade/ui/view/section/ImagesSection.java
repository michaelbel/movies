package org.michaelbel.moviemade.ui.view.section;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.rest.model.v3.Backdrop;
import org.michaelbel.moviemade.rest.model.v3.Poster;
import org.michaelbel.moviemade.ui.interfaces.ImageSectionListener;
import org.michaelbel.moviemade.ui.view.ImagePageView;
import org.michaelbel.moviemade.ui.view.widget.ViewPagerAdapter;

import java.util.List;

public class ImagesSection extends FrameLayout {

    private ViewPager posterViewPager;
    private ViewPager backdropViewPager;

    private TextView postersCountText;
    private TextView backdropsCountText;

    private ViewPagerAdapter postersAdapter;
    private ViewPagerAdapter backdropsAdapter;

    private ImageSectionListener imageSectionListener;

    public ImagesSection(Context context) {
        super(context);

        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.Images);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48,Gravity.START | Gravity.TOP, 16, 0, 16, 0));
        addView(textView);

        LinearLayout imagesLayout = new LinearLayout(context);
        imagesLayout.setOrientation(LinearLayout.HORIZONTAL);
        imagesLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 150, Gravity.TOP, 12, 48, 12, 12));
        addView(imagesLayout);

        postersAdapter = new ViewPagerAdapter(context);

        backdropsAdapter = new ViewPagerAdapter(context);

//------POSTERS-------------------------------------------------------------------------------------

        FrameLayout postersLayout = new FrameLayout(context);
        postersLayout.setOnClickListener(view -> imageSectionListener.onPostersClick(view));
        postersLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER, 1F));
        imagesLayout.addView(postersLayout);

        posterViewPager = new ViewPager(context);
        posterViewPager.setAdapter(postersAdapter);
        posterViewPager.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        postersLayout.addView(posterViewPager);

        FrameLayout postersTitleLayout = new FrameLayout(context);
        postersTitleLayout.setBackgroundColor(0x99000000);
        postersTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        postersLayout.addView(postersTitleLayout);

        postersCountText = new TextView(context);
        postersCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        postersCountText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        postersCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        postersTitleLayout.addView(postersCountText);

//------BACKDROPS-----------------------------------------------------------------------------------

        FrameLayout backdropsLayout = new FrameLayout(context);
        backdropsLayout.setOnClickListener(view -> imageSectionListener.onBackdropClick(view));
        backdropsLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER,2F, 12, 0, 0, 0));
        imagesLayout.addView(backdropsLayout);

        backdropViewPager = new ViewPager(context);
        backdropViewPager.setAdapter(backdropsAdapter);
        backdropViewPager.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        backdropsLayout.addView(backdropViewPager);

        FrameLayout backdropsTitleLayout = new FrameLayout(context);
        backdropsTitleLayout.setBackgroundColor(0x99000000);
        backdropsTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        backdropsLayout.addView(backdropsTitleLayout);

        backdropsCountText = new TextView(context);
        backdropsCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        backdropsCountText.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        backdropsCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        backdropsTitleLayout.addView(backdropsCountText);
    }

    public void addPosters(List<Poster> posters) {
        for (Poster poster : posters) {
            postersAdapter.addLayout(new ImagePageView(getContext()).setImage(poster.filePath));
        }

        postersAdapter.notifyDataSetChanged();
    }

    public void addBackdrops(List<Backdrop> backdrops) {
        for (Backdrop backdrop : backdrops) {
            backdropsAdapter.addLayout(new ImagePageView(getContext()).setImage(backdrop.filePath));
        }

        backdropsAdapter.notifyDataSetChanged();
    }

    public void addImageSectionListener(ImageSectionListener listener) {
        this.imageSectionListener = listener;
    }

    public ImagesSection setPostersCount(int count) {
        postersCountText.setText(getResources().getQuantityString(R.plurals.Posters, count, count));
        return this;
    }

    public ImagesSection setBackdropsCount(int count) {
        backdropsCountText.setText(getResources().getQuantityString(R.plurals.Backdrops, count, count));
        return this;
    }
}