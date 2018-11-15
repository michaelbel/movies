package org.michaelbel.moviemade.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.moviemade.utils.LayoutHelper;
import org.michaelbel.moviemade.R;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ImagesSection extends FrameLayout {

    private TextView postersCountText;
    private TextView backdropsCountText;

    private FrameLayout postersTitleLayout;
    private FrameLayout backdropsTitleLayout;


    public ImagesSection(Context context) {
        super(context);

        TextView textView = new TextView(context);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine();
        textView.setText(R.string.Images);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, 48,Gravity.START | Gravity.TOP, 16, 0, 16, 0));
        addView(textView);

        LinearLayout imagesLayout = new LinearLayout(context);
        imagesLayout.setOrientation(LinearLayout.HORIZONTAL);
        imagesLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, 150, Gravity.TOP, 12, 48, 12, 12));
        addView(imagesLayout);



//------POSTERS-------------------------------------------------------------------------------------

        FrameLayout postersLayout = new FrameLayout(context);
        postersLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER, 1F));
        imagesLayout.addView(postersLayout);

        ViewPager posterViewPager = new ViewPager(context);

        //posterViewPager.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        postersLayout.addView(posterViewPager);

        postersTitleLayout = new FrameLayout(context);
        postersTitleLayout.setBackgroundColor(0x99000000);
        //postersTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        postersLayout.addView(postersTitleLayout);

        postersCountText = new TextView(context);
        postersCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        postersCountText.setTextColor(ContextCompat.getColor(context, R.color.md_white));
        //postersCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        postersTitleLayout.addView(postersCountText);

//------BACKDROPS-----------------------------------------------------------------------------------

        FrameLayout backdropsLayout = new FrameLayout(context);
        //backdropsLayout.setLayoutParams(LayoutHelper.makeLinear(0, LayoutHelper.MATCH_PARENT, Gravity.CENTER,2F, 12, 0, 0, 0));
        imagesLayout.addView(backdropsLayout);

        ViewPager backdropViewPager = new ViewPager(context);
       // backdropViewPager.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        backdropsLayout.addView(backdropViewPager);

        backdropsTitleLayout = new FrameLayout(context);
        backdropsTitleLayout.setBackgroundColor(0x99000000);
       //backdropsTitleLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM));
        backdropsLayout.addView(backdropsTitleLayout);

        backdropsCountText = new TextView(context);
        backdropsCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        backdropsCountText.setTextColor(ContextCompat.getColor(context, R.color.md_white));
      //  backdropsCountText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL, 8, 6, 8, 6));
        backdropsTitleLayout.addView(backdropsCountText);
    }

    /*public void addPosters(List<Image> posters, int size) {
        List<ImagePageView> views = new ArrayList<>();
        for (Image poster : posters) {
            ImagePageView view = new ImagePageView(getContext());
            view.setImage(poster.getFilePath());
            view.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
            views.add(view);
        }

        postersAdapter.addViews(views);
        postersAdapter.notifyDataSetChanged();
        postersCountText.setText(getResources().getQuantityString(R.plurals.Posters, size, size));
    }

    public void addBackdrops(List<Image> backdrops, int size) {
        List<ImagePageView> views = new ArrayList<>();
        for (Image backdrop : backdrops) {
            ImagePageView view = new ImagePageView(getContext());
            view.setImage(backdrop.getFilePath());
            view.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
            view.getPosterImage().setAdjustViewBounds(true);
            view.getPosterImage().setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(view);
        }

        backdropsAdapter.addViews(views);
        backdropsAdapter.notifyDataSetChanged();
        backdropsCountText.setText(getResources().getQuantityString(R.plurals.Backdrops, size, size));
    }*/

    public FrameLayout getPostersLayout() {
        return postersTitleLayout;
    }

    public FrameLayout getBackdropsLayout() {
        return backdropsTitleLayout;
    }
}