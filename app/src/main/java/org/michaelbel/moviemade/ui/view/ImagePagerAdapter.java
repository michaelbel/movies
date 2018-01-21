package org.michaelbel.moviemade.ui.view;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private List<ImagePageView> views = new ArrayList<>();

    public void addViews(List<ImagePageView> views) {
        this.views = views;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImagePageView v = views.get(position);
        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

    /*@Override
    public int getItemPosition(@NonNull Object object) {
        int index = views.indexOf(object);
        if (index == -1) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }*/
}