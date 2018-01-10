package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import org.michaelbel.moviemade.databinding.ActivityCollectionBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.fragment.GenreMoviesFragment;

public class CollectionActivity extends BaseActivity {

    public ActivityCollectionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        binding.progressBar.setVisibility(View.GONE);

        Collection collection = (Collection) getIntent().getSerializableExtra("collection");
        startFragment(GenreMoviesFragment.newInstance(collection.id), binding.fragmentView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}