package org.michaelbel.moviemade.ui.modules.keywords.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.keywords.fragment.KeywordsFragment;
import org.michaelbel.moviemade.utils.IntentsKt;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class KeywordsActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_subtitle) AppCompatTextView toolbarSubtitle;

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keywords);

        Movie movie = (Movie) getIntent().getSerializableExtra(IntentsKt.MOVIE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbarSubtitle.setText(movie.getTitle());

        if (savedInstanceState == null) {
            startFragment(KeywordsFragment.newInstance(movie.getId()), R.id.fragment_view);
        }
    }
}