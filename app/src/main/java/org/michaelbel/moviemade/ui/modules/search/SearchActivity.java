package org.michaelbel.moviemade.ui.modules.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.ui.modules.main.MainActivity;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import shortbread.Shortcut;

@Shortcut(id = "search", rank = 1, icon = R.drawable.ic_shortcut_search, shortLabelRes = R.string.search, backStack = MainActivity.class)
public class SearchActivity extends BaseActivity {

    public static final int SPEECH_REQUEST_CODE = 101;
    public static final int MENU_ITEM_INDEX = 0;
    public static final int MODE_ACTION_CLEAR = 1;
    public static final int MODE_ACTION_VOICE = 2;

    private int iconActionMode;
    private boolean isFilterShowed = false;

    private Menu actionMenu;
    private SearchMoviesFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.shadow_view) View shadowView;
    @BindView(R.id.search_edit_text) AppCompatEditText searchEditText;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        actionMenu = menu;

       /* menu.add(R.string.filter).setIcon(R.drawable.ic_tune).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(item -> {
                showFilter();
                return true;
            });*/

        menu.add(null).setIcon(R.drawable.ic_voice).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setOnMenuItemClickListener(menuItem -> {
                if (iconActionMode == MODE_ACTION_VOICE) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now);
                    startActivityForResult(intent, SPEECH_REQUEST_CODE);
                } else if (iconActionMode == MODE_ACTION_CLEAR) {
                    Objects.requireNonNull(searchEditText.getText()).clear();
                    changeActionIcon();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
                return true;
            });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra(IntentsKt.QUERY);
        fragment = SearchMoviesFragment.newInstance(query);
        if (savedInstanceState == null) {
            startFragment(fragment, R.id.fragment_view);
        }

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        iconActionMode = MODE_ACTION_VOICE;

        searchEditText.setBackground(null);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeActionIcon();
                if (s.length() >= 2) {
                    fragment.presenter.search(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchEditText.setOnEditorActionListener((view, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                fragment.presenter.search(view.getText().toString().trim());
                hideKeyboard(searchEditText);
                return true;
            }

            return false;
        });
        DrawableUtil.INSTANCE.clearCursorDrawable(searchEditText);

        //isFilterShowed = false;
        //filterLayout.setVisibility(GONE);
        //shadowView.setVisibility(GONE);
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
                            searchEditText.setSelection(Objects.requireNonNull(searchEditText.getText()).length());
                            changeActionIcon();
                            fragment.presenter.search(textResults);
                        }
                    }
                }
            }
        }
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (Objects.requireNonNull(searchEditText.getText()).toString().trim().isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_voice);
            } else {
                iconActionMode = MODE_ACTION_CLEAR;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_clear);
            }
        }
    }

    /*private void showFilter() {
        if (isFilterShowed) {
            shadowView.setVisibility(GONE);
            isFilterShowed = false;
        } else {
            shadowView.setVisibility(VISIBLE);
            isFilterShowed = true;
        }

        *//*ObjectAnimator animator = ObjectAnimator.ofFloat(filterLayout, "translationY", -filterLayout.getMeasuredWidth(), 0);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                filterLayout.setVisibility(View.VISIBLE);
            }
        });
        AndroidUtil.INSTANCE.runOnUIThread(animator:: start, 0);*//*
    }*/

    public void hideKeyboard(View view) {
        if (view == null) {
            return;
        }

        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!(imm != null && imm.isActive())) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}