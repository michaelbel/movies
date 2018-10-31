package org.michaelbel.moviemade.ui.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.TrailersFragment;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

public class TrailersActivity extends BaseActivity {

    public int movieId;

    private TrailersFragment fragment;

    public Toolbar toolbar;
    public AppCompatTextView toolbarTitle;
    public AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        movieId = getIntent().getIntExtra("id", 0);
        String movieTitle = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.trailers);

        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        toolbarSubtitle.setText(movieTitle);

        fragment = (TrailersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.loadTrailers(movieId);
        }
    }
}