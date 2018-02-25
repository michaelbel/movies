package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Trailer;
import org.michaelbel.moviemade.ui.fragment.TrailersFragment;
import org.michaelbel.moviemade.ui.view.TitleView;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import java.util.ArrayList;

public class TrailersActivity extends BaseActivity {

    public Toolbar toolbar;
    public TitleView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        //Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        String movieTitle = getIntent().getStringExtra("title");
        ArrayList<Trailer> list = getIntent().getParcelableArrayListExtra("list");

        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle.setTitle(R.string.Trailers);
        toolbarTitle.setSubtitle(movieTitle);

        startFragment(TrailersFragment.newInstance(list), R.id.fragment_view);
    }
}