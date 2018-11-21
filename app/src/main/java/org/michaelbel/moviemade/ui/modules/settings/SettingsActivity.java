package org.michaelbel.moviemade.ui.modules.settings;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar) public Toolbar toolbar;
    @BindView(R.id.toolbar_title) public AppCompatTextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // TODO: toolbar.setTitle?

        if (savedInstanceState == null) {
            startFragment(new SettingsFragment(), R.id.fragment_view);
        }
    }
}