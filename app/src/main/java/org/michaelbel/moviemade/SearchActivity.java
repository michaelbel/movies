package org.michaelbel.moviemade;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.databinding.ActivitySearchBinding;
import org.michaelbel.moviemade.mvp.base.BaseActivity;
import org.michaelbel.moviemade.ui.fragment.SearchCollectionsFragment;
import org.michaelbel.moviemade.ui.fragment.SearchCompaniesFragment;
import org.michaelbel.moviemade.ui.fragment.SearchKeywordsFragment;
import org.michaelbel.moviemade.ui.fragment.SearchMoviesFragment;
import org.michaelbel.moviemade.ui.fragment.SearchPeopleFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.List;
import java.util.Locale;

public class SearchActivity extends BaseActivity {

    public static final int TAB_MOVIES = 0;
    public static final int TAB_PEOPLE = 1;
    public static final int TAB_KEYWORDS = 2;
    public static final int TAB_COLLECTIONS = 3;
    public static final int TAB_COMPANIES = 4;

    private static final int MENU_ITEM_INDEX = 0;
    private final int MODE_ACTION_CLEAR = 1;
    private final int MODE_ACTION_VOICE = 2;
    private final int SPEECH_REQUEST_CODE = 101;

    private int iconActionMode;

    private Menu actionMenu;
    public EditText searchEditText;
    private FragmentsPagerAdapter adapter;

    public ActivitySearchBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        int tab = getIntent().getIntExtra("search_tab", TAB_MOVIES);
        String query = getIntent().getStringExtra("query");

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(view -> finish());

        iconActionMode = MODE_ACTION_VOICE;

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
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeActionIcon();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchEditText.setOnEditorActionListener((view, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                searchInFragment(binding.viewPager.getCurrentItem(), view.getText().toString().trim());
                AndroidUtils.hideKeyboard(searchEditText);
                return true;
            }

            return false;
        });
        toolbarLayout.addView(searchEditText);
        Theme.clearCursorDrawable(searchEditText);

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(SearchMoviesFragment.newInstance(query), R.string.Movies);
        adapter.addFragment(SearchPeopleFragment.newInstance(query), R.string.People);
        adapter.addFragment(SearchKeywordsFragment.newInstance(query), R.string.Keywords);
        adapter.addFragment(SearchCollectionsFragment.newInstance(query), R.string.Collections);
        adapter.addFragment(SearchCompaniesFragment.newInstance(query), R.string.Companies);

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setCurrentItem(tab);
        binding.viewPager.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == TAB_MOVIES) {
                    SearchMoviesFragment fragment = (SearchMoviesFragment) adapter.getItem(TAB_MOVIES);
                    if (fragment.empty()) {
                        if (!searchEditText.getText().toString().isEmpty()) {
                            fragment.presenter.search(searchEditText.getText().toString().trim());
                        }
                    }
                } else if (position == TAB_PEOPLE) {
                    SearchPeopleFragment fragment = (SearchPeopleFragment) adapter.getItem(TAB_PEOPLE);
                    if (fragment.empty()) {
                        if (!searchEditText.getText().toString().isEmpty()) {
                            fragment.presenter.search(searchEditText.getText().toString().trim());
                        }
                    }
                } else if (position == TAB_KEYWORDS) {
                    SearchKeywordsFragment fragment = (SearchKeywordsFragment) adapter.getItem(TAB_KEYWORDS);
                    if (fragment.empty()) {
                        if (!searchEditText.getText().toString().isEmpty()) {
                            fragment.presenter.search(searchEditText.getText().toString().trim());
                        }
                    }
                } else if (position == TAB_COLLECTIONS) {
                    SearchCollectionsFragment fragment = (SearchCollectionsFragment) adapter.getItem(TAB_COLLECTIONS);
                    if (fragment.empty()) {
                        if (!searchEditText.getText().toString().isEmpty()) {
                            fragment.presenter.search(searchEditText.getText().toString().trim());
                        }
                    }
                } else if (position == TAB_COMPANIES) {
                    SearchCompaniesFragment fragment = (SearchCompaniesFragment) adapter.getItem(TAB_COMPANIES);
                    if (fragment.empty()) {
                        if (!searchEditText.getText().toString().isEmpty()) {
                            fragment.presenter.search(searchEditText.getText().toString().trim());
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        binding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, Theme.primaryColor()));
        binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, Theme.accentColor()));
        binding.tabLayout.setTabTextColors(ContextCompat.getColor(this, Theme.secondaryTextColor()), ContextCompat.getColor(this, Theme.accentColor()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionMenu = menu;

        menu.add(null)
            .setIcon(R.drawable.ic_voice)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                if (iconActionMode == MODE_ACTION_VOICE) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.SpeakNow);
                    startActivityForResult(intent, SPEECH_REQUEST_CODE);
                } else if (iconActionMode == MODE_ACTION_CLEAR) {
                    searchEditText.getText().clear();
                    changeActionIcon();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                }
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (results != null && results.size() > 0) {
                    String textResults = results.get(0);
                    if (!TextUtils.isEmpty(textResults)) {
                        if (searchEditText != null) {
                            searchEditText.setText(textResults);
                            searchEditText.setSelection(searchEditText.getText().length());
                            changeActionIcon();
                            searchInFragment(binding.viewPager.getCurrentItem(), textResults);
                        }
                    }
                }
            }
        }
    }

    private void searchInFragment(int position, String query) {
        if (position == TAB_MOVIES) {
            SearchMoviesFragment fragment = (SearchMoviesFragment) adapter.getItem(position);
            fragment.presenter.search(query);
        } else if (position == TAB_PEOPLE) {
            SearchPeopleFragment fragment = (SearchPeopleFragment) adapter.getItem(position);
            fragment.presenter.search(query);
        } else if (position == TAB_KEYWORDS) {
            SearchKeywordsFragment fragment = (SearchKeywordsFragment) adapter.getItem(position);
            fragment.presenter.search(query);
        } else if (position == TAB_COLLECTIONS) {
            SearchCollectionsFragment fragment = (SearchCollectionsFragment) adapter.getItem(position);
            fragment.presenter.search(query);
        } else if (position == TAB_COMPANIES) {
            SearchCompaniesFragment fragment = (SearchCompaniesFragment) adapter.getItem(TAB_COMPANIES);
            fragment.presenter.search(query);
        }
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (searchEditText.getText().toString().trim().isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(Theme.getIcon(R.drawable.ic_voice, ContextCompat.getColor(this, Theme.primaryTextColor())));
            } else {
                iconActionMode = MODE_ACTION_CLEAR;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(Theme.getIcon(R.drawable.ic_clear, ContextCompat.getColor(this, Theme.primaryTextColor())));
            }
        }
    }
}