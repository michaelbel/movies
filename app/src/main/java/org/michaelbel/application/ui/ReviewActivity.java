package org.michaelbel.application.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.ui.fragment.ReviewDetailsFragment;

public class ReviewActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView toolbarTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarTextView = findViewById(R.id.toolbar_title);

        if (savedInstanceState == null) {
            String reviewId = getIntent().getStringExtra("reviewId");
            setRootFragment(ReviewDetailsFragment.newInstance(reviewId));
        }
    }

    public void setRootFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }
}