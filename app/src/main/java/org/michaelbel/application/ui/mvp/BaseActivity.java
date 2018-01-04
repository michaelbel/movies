package org.michaelbel.application.ui.mvp;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

@SuppressWarnings("all")
public class BaseActivity extends AppCompatActivity implements BaseActivityModel {

    @Override
    public void startFragment(Fragment fragment, View container) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), fragment)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, View container, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(container.getId(), fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void startFragment(Fragment fragment, int containerId, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager()
                .popBackStack();
    }
}