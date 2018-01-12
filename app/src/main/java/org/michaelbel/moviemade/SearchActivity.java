package org.michaelbel.moviemade;

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

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.databinding.ActivitySearchBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.SearchMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

public class SearchActivity extends BaseActivity {

    public EditText searchEditText;
    public ActivitySearchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

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
        adapter.addFragment(SearchMoviesFragment.newInstance(query), R.string.Movies);
        //adapter.addFragment(SearchPeopleFragment.newInstance(query), R.string.People);

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}