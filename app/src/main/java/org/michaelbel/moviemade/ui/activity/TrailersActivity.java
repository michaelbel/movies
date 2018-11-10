package org.michaelbel.moviemade.ui.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.TrailersFragment;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersActivity extends BaseActivity {

    public int movieId;
    private TrailersFragment fragment;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    public AppCompatTextView toolbarTitle;

    @BindView(R.id.toolbar_subtitle)
    public AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
        ButterKnife.bind(this);

        movieId = getIntent().getIntExtra("id", 0);
        String movieTitle = getIntent().getStringExtra("title");

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        toolbarTitle.setText(R.string.trailers);
        toolbarSubtitle.setText(movieTitle);

        fragment = (TrailersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.loadTrailers(movieId);
        }
    }
}