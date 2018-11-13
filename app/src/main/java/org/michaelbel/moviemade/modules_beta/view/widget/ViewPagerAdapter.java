package org.michaelbel.moviemade.modules_beta.view.widget;

import android.content.Context;
import androidx.annotation.StringRes;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<View> layouts = new ArrayList<>();
    private List<CharSequence> titles = new ArrayList<>();

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    public void addLayout(View view) {
        layouts.add(view);
    }

    public void addLayout(View view, CharSequence title) {
        layouts.add(view);
        titles.add(title);
    }

    public void addLayout(View view, @StringRes int stringId) {
        addLayout(view, context.getText(stringId));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return layouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        FrameLayout layout = new FrameLayout(context);
        layout.addView(layouts.get(position));

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}