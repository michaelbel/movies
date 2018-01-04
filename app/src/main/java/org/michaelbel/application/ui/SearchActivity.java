package org.michaelbel.application.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.michaelbel.application.R;
import org.michaelbel.application.databinding.ActivitySearchBinding;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.ui.mvp.BaseActivity;
import org.michaelbel.application.ui.mvp.BaseActivityModel;
import org.michaelbel.application.ui.mvp.BasePresenter;
import org.michaelbel.application.ui.fragment.SearchFragment;
import org.michaelbel.application.ui.view.widget.FragmentsPagerAdapter;

@SuppressWarnings("all")
public class SearchActivity extends BaseActivity implements BaseActivityModel {

    public EditText searchEditText;
    public ActivitySearchBinding binding;
    private BasePresenter<BaseActivityModel> presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        presenter = new BasePresenter<>();
        presenter.attachView(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

        FrameLayout toolbarLayout = new FrameLayout(this);
        toolbarLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        binding.toolbar.addView(toolbarLayout);

        searchEditText = new EditText(this);
        searchEditText.setLines(1);
        searchEditText.setMaxLines(1);
        searchEditText.setSingleLine();
        searchEditText.setHint(R.string.Search);
        searchEditText.setBackgroundDrawable(null);
        searchEditText.setTypeface(Typeface.DEFAULT);
        searchEditText.setEllipsize(TextUtils.TruncateAt.END);
        searchEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        searchEditText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        searchEditText.setTextColor(ContextCompat.getColor(this, Theme.primaryTextColor()));
        searchEditText.setHintTextColor(ContextCompat.getColor(this, Theme.hindTextColor()));
        searchEditText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        toolbarLayout.addView(searchEditText);
        Theme.clearCursorDrawable(searchEditText);

        String query = getIntent().getStringExtra("query");

        FragmentsPagerAdapter adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(SearchFragment.newInstance(query), R.string.Movies);

        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startMovie(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }
}