package org.michaelbel.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivitySettingsBinding;
import org.michaelbel.application.ui.base.BaseActivity;
import org.michaelbel.application.ui.base.BaseActivityModel;
import org.michaelbel.application.ui.base.BasePresenter;
import org.michaelbel.application.ui.fragment.SettingsFragment;

@SuppressWarnings("all")
public class SettingsActivity extends BaseActivity implements BaseActivityModel {

    public ActivitySettingsBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        setSupportActionBar(binding.toolbar);
        startFragment(new SettingsFragment(), binding.fragmentLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}