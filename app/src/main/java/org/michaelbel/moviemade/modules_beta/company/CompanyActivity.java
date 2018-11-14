package org.michaelbel.moviemade.modules_beta.company;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.dao.Company;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.AndroidUtilsDev;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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

        toolbarTitle.setText(company.getName());

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        startFragment(CompanyMoviesFragment.newInstance(company.getId()), R.id.fragment_view);
    }
}