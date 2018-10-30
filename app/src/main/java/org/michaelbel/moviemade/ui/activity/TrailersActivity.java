package org.michaelbel.moviemade.ui.activity;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui_old.fragment.TrailersFragment;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

public class TrailersActivity extends BaseActivity {

    public Toolbar toolbar;
    public AppCompatTextView toolbarTitle;
    public AppCompatTextView toolbarSubtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        String movieTitle = getIntent().getStringExtra("title");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.trailers);

        toolbarSubtitle = findViewById(R.id.toolbar_subtitle);
        toolbarSubtitle.setText(movieTitle);

        startFragment(new TrailersFragment(), R.id.fragment_view);
    }
}