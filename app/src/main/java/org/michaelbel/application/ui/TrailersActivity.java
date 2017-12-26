package org.michaelbel.application.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.michaelbel.application.R;
import org.michaelbel.application.ui.fragment.TrailersFragment;
import org.michaelbel.application.ui.view.TitleView;

public class TrailersActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        titleView = findViewById(R.id.toolbar_title);

        if (savedInstanceState == null) {
            int movieId = getIntent().getIntExtra("movieId", 0);
            String title = getIntent().getStringExtra("movieTitle");
            startFragment(TrailersFragment.newInstance(movieId, title));
        }
    }

    public void startFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }
}