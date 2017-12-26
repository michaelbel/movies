package org.michaelbel.application.ui.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentsPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<CharSequence> mTitles = new ArrayList<>();

    public FragmentsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    public void addFragment(Fragment fragment, CharSequence title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    public void addFragment(Fragment fragment, @StringRes int stringId) {
        addFragment(fragment, mContext.getText(stringId));
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}