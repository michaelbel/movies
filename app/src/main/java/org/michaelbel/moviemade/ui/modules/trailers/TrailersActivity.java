package org.michaelbel.moviemade.ui.modules.trailers;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class TrailersActivity extends BaseActivity {

    // TODO make private.
    // TODO make add getter
    public Movie movie;
    private TrailersFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.setOnClickListener(v -> fragment.recyclerView.smoothScrollToPosition(0));

        toolbarSubtitle.setText(movie.getTitle());

        fragment = (TrailersFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment != null) {
            fragment.presenter.getVideos(movie.getId());
        }
    }
}