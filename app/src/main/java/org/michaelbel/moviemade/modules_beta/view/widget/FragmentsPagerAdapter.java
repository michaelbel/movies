package org.michaelbel.moviemade.modules_beta.view.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentsPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<CharSequence> mTitles = new ArrayList<>();

    public FragmentsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mContext = context;
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void addFragment(Fragment fragment, CharSequence title) {
        mFragments.add(fragment);
        mTitles.add(title);
    }

    public void addFragments(List<Fragment> fragments, List<CharSequence> titles) {
        mFragments.addAll(fragments);
        mTitles.addAll(titles);
    }

    public void addFragment(Fragment fragment, @StringRes int stringId) {
        addFragment(fragment, mContext.getText(stringId));
    }

    public void clear() {
        mFragments.clear();
        mTitles.clear();
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
        return mTitles.isEmpty() ? null : mTitles.get(position);
    }
}