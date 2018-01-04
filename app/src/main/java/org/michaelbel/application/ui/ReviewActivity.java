package org.michaelbel.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivityReviewBinding;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Review;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.ReviewFragment;
import org.michaelbel.application.util.AndroidUtilsDev;

@SuppressWarnings("all")
public class ReviewActivity extends BaseActivity implements BaseActivityModel {

    public ActivityReviewBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) binding.toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);

        binding.toolbar.setLayoutParams(params);
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            Review review = (Review) getIntent().getParcelableExtra("review");
            Movie movie = (Movie) getIntent().getSerializableExtra("movie");

            startFragment(ReviewFragment.newInstance(review, movie), binding.fragmentLayout);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}