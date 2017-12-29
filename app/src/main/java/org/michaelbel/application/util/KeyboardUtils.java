package org.michaelbel.application.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressWarnings("all")
public class KeyboardUtils {

    public static void showKeyboard(@NonNull View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            // todo Error
        }
    }

    public static boolean isKeyboardShowed(@NonNull View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputManager.isActive(view);
        } catch (Exception e) {
            // todo Error
        }

        return false;
    }

    public static void hideKeyboard(@NonNull View view) {
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            // todo Error
        }
    }
}