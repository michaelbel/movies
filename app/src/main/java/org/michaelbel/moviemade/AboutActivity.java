package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.michaelbel.moviemade.databinding.ActivityAboutBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.AboutFragment;

public class AboutActivity extends BaseActivity {

    public ActivityAboutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);

        setSupportActionBar(binding.toolbar);
        startFragment(new AboutFragment(), binding.fragmentView);
    }
}