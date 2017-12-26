package org.michaelbel.application.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressWarnings("all")
public class KeyboardUtils {

    private static final String TAG = KeyboardUtils.class.getSimpleName();

    public static void showKeyboard(@NonNull View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Show keyboard error");
            //FirebaseCrash.report(e);
        }
    }

    public static boolean isKeyboardShowed(@NonNull View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputManager.isActive(view);
        } catch (Exception e) {
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Is keyboard showed keyboard");
            //FirebaseCrash.report(e);
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
            //FirebaseCrash.logcat(Log.ERROR, "e_message", "Hide keyboard error");
            //FirebaseCrash.report(e);
        }
    }
}