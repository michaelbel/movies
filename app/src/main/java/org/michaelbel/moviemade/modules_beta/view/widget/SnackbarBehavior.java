package org.michaelbel.moviemade.modules_beta.view.widget;

import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.behavior.SwipeDismissBehavior;
import android.view.View;

@SuppressWarnings("all")
public class SnackbarBehavior extends SwipeDismissBehavior<Snackbar.SnackbarLayout> {

    @Override
    public boolean canSwipeDismissView(@NonNull View view) {
        return false;
    }
}