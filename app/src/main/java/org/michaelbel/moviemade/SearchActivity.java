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
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
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
import org.michaelbel.moviemade.ui.fragment.SearchMoviesFragment;
import org.michaelbel.moviemade.ui.view.widget.FragmentsPagerAdapter;

import java.util.List;
import java.util.Locale;

public class SearchActivity extends BaseActivity {

    private final int MENU_ITEM_INDEX = 0;
    private final int MODE_ACTION_CLEAR = 1;
    private final int MODE_ACTION_VOICE = 2;
    private final int SPEECH_REQUEST_CODE = 101;

    private int iconActionMode;

    public Menu actionMenu;

    public EditText searchEditText;
    public ActivitySearchBinding binding;

    private FragmentsPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(binding.toolbar);

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
        toolbarLayout.addView(searchEditText);
        Theme.clearCursorDrawable(searchEditText);

        String query = getIntent().getStringExtra("query");

        adapter = new FragmentsPagerAdapter(this, getSupportFragmentManager());
        adapter.addFragment(SearchMoviesFragment.newInstance(query), R.string.Movies);
        //adapter.addFragment(SearchPeopleFragment.newInstance(query), R.string.People);

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setBackgroundColor(ContextCompat.getColor(this, Theme.backgroundColor()));

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

        menu.add("")
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

                            SearchMoviesFragment fragment = (SearchMoviesFragment) adapter.getItem(0);
                            fragment.presenter.search(textResults);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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