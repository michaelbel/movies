package org.michaelbel.moviemade.ui.base;

import androidx.fragment.app.Fragment;
import android.view.View;

public interface BaseMvp {

    void startFragment(Fragment fragment, View container);

    void startFragment(Fragment fragment, int containerId);

    void startFragment(Fragment fragment, View container, String tag);

    void startFragment(Fragment fragment, int containerId, String tag);

    void finishFragment();
}