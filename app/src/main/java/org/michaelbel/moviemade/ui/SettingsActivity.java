package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.databinding.ActivitySettingsBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.SettingsFragment;

public class SettingsActivity extends BaseActivity {

    public ActivitySettingsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);

        setSupportActionBar(binding.toolbar);
        startFragment(new SettingsFragment(), binding.fragmentView);
    }
}