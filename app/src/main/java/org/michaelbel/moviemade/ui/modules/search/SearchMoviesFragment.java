package org.michaelbel.moviemade.ui.modules.search;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import org.jetbrains.annotations.NotNull;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.data.entity.Movie;
import org.michaelbel.moviemade.ui.GridSpacingItemDecoration;
import org.michaelbel.moviemade.ui.base.BaseFragment;
import org.michaelbel.moviemade.ui.modules.main.adapter.MoviesAdapter;
import org.michaelbel.moviemade.ui.modules.main.adapter.OnMovieClickListener;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.BuildUtil;
import org.michaelbel.moviemade.utils.DeviceUtil;
import org.michaelbel.moviemade.utils.DrawableUtil;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class SearchMoviesFragment extends BaseFragment implements SearchContract.View, OnMovieClickListener {

    private static final String KEY_MENU_ICON = "icon";

    private static final int SPEECH_REQUEST_CODE = 101;
    private static final int MENU_ITEM_INDEX = 0;
    private static final int ITEM_CLR = 1;
    private static final int ITEM_MIC = 2;

    private int iconActionMode = ITEM_MIC;

    private Menu actionMenu;
    private MoviesAdapter adapter;
    private SearchActivity activity;

    @Inject SearchContract.Presenter presenter;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private AppCompatEditText searchView;

    static SearchMoviesFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(IntentsKt.QUERY, query);

        SearchMoviesFragment fragment = new SearchMoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                List<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (results != null && results.size() > 0) {
                    String textResults = results.get(0);
                    if (!TextUtils.isEmpty(textResults)) {
                        if (searchView != null) {
                            searchView.setText(textResults);
                            searchView.setSelection(searchView.getText().length());
                            changeActionIcon();
                            presenter.search(textResults);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        activity = (SearchActivity) getActivity();

        if (activity != null) {
            Moviemade.get(activity).getFragmentComponent().inject(this);
        }
        presenter.setView(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        actionMenu = menu;
        int icon = iconActionMode == ITEM_MIC ? R.drawable.ic_voice : R.drawable.ic_clear;
        menu.add(null).setIcon(icon).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM).setOnMenuItemClickListener(item -> {
            if (iconActionMode == ITEM_CLR) {
                if (searchView.getText() != null) {
                    searchView.getText().clear();
                }
                changeActionIcon();

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
                }
            } else {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.speak_now);
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
            return true;
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int spanCount = activity.getResources().getInteger(R.integer.movies_span_layout_count);

        adapter = new MoviesAdapter(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, spanCount));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, DeviceUtil.INSTANCE.dp(activity, 3)));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.loadNextResults();
                }
            }
        });

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        emptyView = view.findViewById(R.id.empty_view);
        emptyView.setMode(EmptyViewMode.MODE_NO_RESULTS);

        String readyQuery = getArguments() != null ? getArguments().getString(IntentsKt.QUERY) : null;

        searchView = activity.getSearchEditText();
        searchView.setBackground(null);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeActionIcon();
                if (s.toString().trim().length() >= 2) {
                    presenter.search(s.toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(v.getText().toString().trim());
                hideKeyboard(searchView);
                return true;
            }
            return false;
        });

        if (readyQuery == null) {
            searchView.setSelection(Objects.requireNonNull(activity.getSearchEditText().getText()).length());
        } else {
            searchView.setText(readyQuery);
            searchView.setSelection(Objects.requireNonNull(activity.getSearchEditText().getText()).length());

            hideKeyboard(activity.getSearchEditText());
            presenter.search(readyQuery);
        }
        DrawableUtil.INSTANCE.clearCursorDrawable(searchView);

        if (savedInstanceState != null) {
            iconActionMode = savedInstanceState.getInt(KEY_MENU_ICON);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_MENU_ICON, iconActionMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void searchStart() {
        adapter.getMovies().clear();
        adapter.notifyDataSetChanged();

        emptyView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void setMovies(@NotNull List<Movie> movies) {
        progressBar.setVisibility(View.GONE);
        adapter.addMovies(movies);
    }

    @Override
    public void setError(int mode) {
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);

        if (BuildUtil.INSTANCE.isEmptyApiKey()) {
            emptyView.setValue(R.string.error_empty_api_key);
        }
    }

    @Override
    public void onMovieClick(@NotNull Movie movie, @NotNull View view) {
        activity.startMovie(movie);
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (searchView.getText().toString().trim().isEmpty()) {
                iconActionMode = ITEM_MIC;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_voice);
            } else {
                iconActionMode = ITEM_CLR;
                actionMenu.getItem(MENU_ITEM_INDEX).setIcon(R.drawable.ic_clear);
            }
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null && !(imm.isActive())) {
            return;
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}