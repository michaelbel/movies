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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.ui.base.BaseActivity;
import org.michaelbel.moviemade.utils.AndroidUtils;
import org.michaelbel.moviemade.utils.DrawableUtil;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressWarnings("all")
public class SearchActivity extends BaseActivity {

    public static final int MENU_ITEM_INDEX = 0;
    public static final int SPEECH_REQUEST_CODE = 101;
    public static final int MODE_ACTION_CLEAR = 1;
    public static final int MODE_ACTION_VOICE = 2;

    private int iconActionMode;

    private Menu actionMenu;
    private SearchMoviesFragment fragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.search_edit_text) EditText searchEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        String query = getIntent().getStringExtra("query");
        fragment = SearchMoviesFragment.newInstance(query);
        if (savedInstanceState == null) {
            startFragment(fragment, R.id.fragment_view);
        }

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
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
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchEditText.setOnEditorActionListener((view, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                fragment.presenter.search(view.getText().toString().trim());
                AndroidUtils.hideKeyboard(searchEditText);
                return true;
            }

            return false;
        });
        DrawableUtil.INSTANCE.clearCursorDrawable(searchEditText);
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
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now);
                    startActivityForResult(intent, SPEECH_REQUEST_CODE);
                } else if (iconActionMode == MODE_ACTION_CLEAR) {
                    searchEditText.getText().clear();
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
                            fragment.presenter.search(textResults);
                        }
                    }
                }
            }
        }
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (searchEditText.getText().toString().trim().isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_voice);
            } else {
                iconActionMode = MODE_ACTION_CLEAR;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_clear);
            }
        }
    }
}