package org.michaelbel.application.ui.view.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.View;

@SuppressWarnings("all")
public class SnackbarBehavior extends SwipeDismissBehavior<Snackbar.SnackbarLayout> {

    @Override
    public boolean canSwipeDismissView(@NonNull View view) {
        return false;
    }
}