package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.ui.fragment.GenreMoviesFragment;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class GenreActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Genre genre = getIntent().getParcelableExtra("genre");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(genre.name);

        startFragment(GenreMoviesFragment.newInstance(genre.id), R.id.fragment_view);
    }
}