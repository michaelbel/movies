package org.michaelbel.moviemade.modules_beta.person;

import android.view.View;

public interface PersonViewListener {

    void onBirthPlaceClick(View view);

    void onBirthPlaceLongClick(View view);

    void onWebpageClick(View view, int position);
}