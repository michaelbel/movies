package org.michaelbel.moviemade.ui.modules.about;

import android.os.Bundle;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.about.fragments.AboutFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title) AppCompatTextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_about);

        if (savedInstanceState == null) {
            startFragment(new AboutFragment(), R.id.fragment_view);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public AppCompatTextView getToolbarTitle() {
        return toolbarTitle;
    }
}