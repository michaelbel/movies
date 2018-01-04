package org.michaelbel.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.view.MenuItem;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityPopularPeopleBinding;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.PopularPeopleFragment;
import org.michaelbel.application.util.AndroidUtilsDev;

@SuppressWarnings("all")
public class PopularPeopleActivity extends BaseActivity implements BaseActivityModel {

    public ActivityPopularPeopleBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_popular_people);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(R.string.PopularPeople);

        startFragment(PopularPeopleFragment.newInstance(), binding.fragmentLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}