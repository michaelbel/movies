package org.michaelbel.moviemade.mvp.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.arellomobile.mvp.MvpView;

public interface BaseModel extends MvpView {

    void startFragment(Fragment fragment, View container);

    void startFragment(Fragment fragment, int containerId);

    void startFragment(Fragment fragment, View container, String tag);

    void startFragment(Fragment fragment, int containerId, String tag);

    void finishFragment();
}