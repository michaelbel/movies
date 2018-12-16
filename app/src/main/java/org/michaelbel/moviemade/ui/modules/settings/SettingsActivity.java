package org.michaelbel.moviemade.ui.modules.settings;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class SettingsActivity extends BaseActivity {

    // TODO make private.
    // TODO make add getter
    @BindView(R.id.toolbar) public Toolbar toolbar;
    // TODO make private.
    // TODO make add getter
    @BindView(R.id.toolbar_title) public AppCompatTextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            startFragment(new SettingsFragment(), R.id.fragment_view);
        }
    }
}