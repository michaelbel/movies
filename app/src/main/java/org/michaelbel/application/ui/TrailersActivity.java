package org.michaelbel.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityTrailersBinding;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.base.BaseActivity;
import org.michaelbel.application.ui.base.BaseActivityModel;
import org.michaelbel.application.ui.base.BasePresenter;
import org.michaelbel.application.ui.fragment.TrailersFragment;
import org.michaelbel.application.util.AndroidUtilsDev;

import java.util.ArrayList;

public class TrailersActivity extends BaseActivity implements BaseActivityModel {

    public ActivityTrailersBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trailers);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            ArrayList<Trailer> list = (ArrayList<Trailer>) getIntent().getSerializableExtra("list");
            Movie movie = (Movie) getIntent().getSerializableExtra("movie");
            startFragment(TrailersFragment.newInstance(movie, list), binding.fragmentLayout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}