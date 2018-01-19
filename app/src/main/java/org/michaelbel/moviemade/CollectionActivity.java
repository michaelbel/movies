package org.michaelbel.moviemade;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.michaelbel.moviemade.databinding.ActivityCollectionBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.rest.model.v3.Collection;
import org.michaelbel.moviemade.ui.fragment.CollectionMoviesFragment;
import org.michaelbel.moviemade.util.AndroidUtilsDev;

public class CollectionActivity extends BaseActivity {

    public ActivityCollectionBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);

        Collection collection = (Collection) getIntent().getSerializableExtra("collection");

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        binding.toolbar.setLayoutParams(AndroidUtilsDev.getLayoutParams(binding.toolbar));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        binding.toolbarTitle.setText(collection.name);
        binding.progressBar.setVisibility(View.GONE);

        startFragment(CollectionMoviesFragment.newInstance(collection.id), binding.fragmentView);
    }
}