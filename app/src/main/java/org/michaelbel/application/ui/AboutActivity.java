package org.michaelbel.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityAboutBinding;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.AboutFragment;

@SuppressWarnings("all")
public class AboutActivity extends BaseActivity implements BaseActivityModel {

    public ActivityAboutBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        setSupportActionBar(binding.toolbar);
        startFragment(new AboutFragment(), binding.fragmentLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}