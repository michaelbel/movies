package org.michaelbel.moviemade.modules_beta.genres;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Genre;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class GenreActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        Genre genre = getIntent().getParcelableExtra("genre");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(genre.name);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        startFragment(GenreMoviesFragment.newInstance(genre.id), R.id.fragment_view);
    }
}