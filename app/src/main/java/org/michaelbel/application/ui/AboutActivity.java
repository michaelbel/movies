package org.michaelbel.application.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.ui.fragment.AboutFragment;

public class AboutActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView toolbarTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);

        setRootFragment(new AboutFragment());
    }

    public void setRootFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }

    public void startFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.fragment_layout, fragment)
                .addToBackStack(tag)
                .commit();
    }

    public void finishFragment() {
        getSupportFragmentManager().popBackStack();
    }
}