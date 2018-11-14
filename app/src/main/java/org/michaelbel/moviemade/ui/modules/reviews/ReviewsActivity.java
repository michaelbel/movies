package org.michaelbel.moviemade.ui.modules.reviews;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsActivity extends BaseActivity {

    public int movieId;
    private ReviewsFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);

        movieId = getIntent().getIntExtra("id", 0);
        String movieTitle = getIntent().getStringExtra("title");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        toolbarTitle.setText(R.string.reviews);
        toolbarSubtitle.setText(movieTitle);

        fragment = (ReviewsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.loadReviews(movieId);
        }
    }
}