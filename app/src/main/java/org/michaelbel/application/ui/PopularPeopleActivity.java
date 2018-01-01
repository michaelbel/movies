package org.michaelbel.application.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.ui.fragment.PopularPeopleFragment;
import org.michaelbel.application.util.AndroidUtilsDev;

@SuppressWarnings("all")
public class PopularPeopleActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public TextView toolbarTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AndroidUtilsDev.floatingToolbar() ? AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP : 0);
        toolbar.setLayoutParams(params);

        toolbarTextView = findViewById(R.id.toolbar_title);
        toolbarTextView.setText(R.string.PopularPeople);

        setRootFragment(PopularPeopleFragment.newInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setRootFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_layout, fragment)
                .commit();
    }
}