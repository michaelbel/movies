package org.michaelbel.moviemade.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.databinding.ActivityCompanyBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Company;
import org.michaelbel.moviemade.ui.fragment.CompanyMoviesFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class CompanyActivity extends BaseActivity {

    public ActivityCompanyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company);

        Company company = getIntent().getParcelableExtra("company");

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbarTitle.setText(company.name);
        binding.progressBar.setVisibility(View.GONE);

        startFragment(CompanyMoviesFragment.newInstance(company.id), binding.fragmentView);
    }
}