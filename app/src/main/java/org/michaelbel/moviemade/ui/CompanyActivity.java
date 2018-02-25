package org.michaelbel.moviemade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.fragment.CompanyMoviesFragment;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

public class CompanyActivity extends BaseActivity {

    public Toolbar toolbar;
    public TextView toolbarTitle;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Company company = getIntent().getParcelableExtra("company");

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbar_title);

        toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(toolbar));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        toolbarTitle.setText(company.name);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        startFragment(CompanyMoviesFragment.newInstance(company.id), R.id.fragment_view);
    }
}