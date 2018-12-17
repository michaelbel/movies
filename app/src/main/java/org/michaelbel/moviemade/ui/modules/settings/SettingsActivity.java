package org.michaelbel.moviemade.ui.modules.settings;

import android.os.Bundle;

import com.google.android.gms.analytics.HitBuilders;

import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.DeviceUtil;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;

    public Toolbar getToolbar() {
        return toolbar;
    }

    public AppCompatTextView getToolbarTitle() {
        return toolbarTitle;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            startFragment(new SettingsFragment(), R.id.fragment_view);
        }

        Moviemade.get(this).getTracker().send(new HitBuilders.EventBuilder().setCategory("Action").setAction("Settings Activity visited").build());
    }
}