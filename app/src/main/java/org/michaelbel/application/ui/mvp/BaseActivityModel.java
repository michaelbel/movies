package org.michaelbel.application.ui.mvp;

import android.support.v4.app.Fragment;
import android.view.View;

@SuppressWarnings("all")
public interface BaseActivityModel {

    void startFragment(Fragment fragment, View container);

    void startFragment(Fragment fragment, int containerId);

    void startFragment(Fragment fragment, View container, String tag);

    void startFragment(Fragment fragment, int containerId, String tag);

    void finishFragment();
}