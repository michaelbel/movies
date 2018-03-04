package org.michaelbel.moviemade.ui.interfaces;

import android.view.View;

public interface PersonViewListener {

    void onBirthPlaceClick(View view);

    void onBirthPlaceLongClick(View view);

    void onWebpageClick(View view, int position);
}