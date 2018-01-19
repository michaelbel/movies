package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import org.michaelbel.moviemade.databinding.ActivityCompanyBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.Company;
import org.michaelbel.moviemade.ui.fragment.CompanyMoviesFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class CompanyActivity extends BaseActivity {

    public ActivityCompanyBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_company);

        Company company = (Company) getIntent().getSerializableExtra("company");

        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.toolbarTitle.setText(company.name);
        binding.progressBar.setVisibility(View.GONE);

        startFragment(CompanyMoviesFragment.newInstance(company.id), binding.fragmentView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}