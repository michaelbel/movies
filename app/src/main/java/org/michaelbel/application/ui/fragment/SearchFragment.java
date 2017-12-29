package org.michaelbel.application.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.SEARCH;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.response.MovieResponse;
import org.michaelbel.application.ui.SearchActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.movie.MovieViewList;
import org.michaelbel.application.ui.view.widget.RecyclerListView;
import org.michaelbel.application.util.AndroidUtils;
import org.michaelbel.application.util.KeyboardUtils;
import org.michaelbel.application.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

@SuppressWarnings("all")
public class SearchFragment extends Fragment {

    private final int SPEECH_REQUEST_CODE = 101;

    private final int MODE_ACTION_CLEAR = 1;
    private final int MODE_ACTION_VOICE = 2;

    private int iconActionMode;

    private Menu actionMenu;
    private SearchActivity activity;
    private SearchMovieAdapter adapter;
    private List<Movie> searchResults = new ArrayList<>();

    private TextView emptyView;
    private ProgressBar progressBar;
    private EditText searchEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SearchActivity) getActivity();
        setHasOptionsMenu(true);

        iconActionMode = MODE_ACTION_VOICE;

        FrameLayout toolbarLayout = new FrameLayout(activity);
        toolbarLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        activity.toolbar.addView(toolbarLayout);

        searchEditText = new EditText(activity);
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
        searchEditText.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        searchEditText.setHintTextColor(ContextCompat.getColor(activity, Theme.hindTextColor()));
        searchEditText.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.CENTER_VERTICAL));
        searchEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                search(textView.getText().toString().trim());
                KeyboardUtils.hideKeyboard(searchEditText);
                return true;
            }
            return false;
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                changeActionIcon();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        toolbarLayout.addView(searchEditText);
        Theme.clearCursorDrawable(searchEditText);

        // todo Do not work!
        InputMethodManager imm = (InputMethodManager) searchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT);
        searchEditText.requestFocus();

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = new TextView(activity);
        emptyView.setText(R.string.NoResults);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        adapter = new SearchMovieAdapter();

        RecyclerListView recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            Movie movie = searchResults.get(position);
            activity.startMovie(movie);
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchEditText.performClick();

        // todo Do now work!
        searchEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) searchEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(activity.getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        //searchEditText.requestFocus();
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
                        if (searchEditText != null) {
                            searchEditText.setText(textResults);
                            searchEditText.setSelection(searchEditText.getText().length());
                            search(textResults);
                            changeActionIcon();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        actionMenu = menu;

        actionMenu.add(null)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            .setIcon(Theme.getIcon(R.drawable.ic_voice, ContextCompat.getColor(activity, Theme.iconActiveColor())))
            .setOnMenuItemClickListener(item -> {
                if (iconActionMode == MODE_ACTION_VOICE) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.SpeakNow);
                    startActivityForResult(intent, SPEECH_REQUEST_CODE);
                } else {
                    searchEditText.getText().clear();
                    changeActionIcon();
                }
                return true;
            });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (searchEditText.getText().toString().trim().isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE;
                actionMenu.getItem(0).setIcon(Theme.getIcon(R.drawable.ic_voice, ContextCompat.getColor(activity, Theme.iconActiveColor())));
            } else {
                iconActionMode = MODE_ACTION_CLEAR;
                actionMenu.getItem(0).setIcon(Theme.getIcon(R.drawable.ic_clear, ContextCompat.getColor(activity, Theme.iconActiveColor())));
            }
        }
    }

    private void search(String query) {
        searchResults.clear();
        emptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            onLoadError();
        } else {
            performSearch(query);
        }
    }

    public void performSearch(String query) {
        //String finalQuery = query.trim().replace(" ", "-");
        SEARCH service = ApiFactory.getRetrofit().create(SEARCH.class);
        Call<MovieResponse> call = service.searchMovies(Url.SEARCH_FOR_MOVIES, Url.TMDB_API_KEY, Url.en_US, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (AndroidUtils.includeAdult()) {
                        searchResults.addAll(response.body().movieList);
                    } else {
                        for (Movie movie : response.body().movieList) {
                            if (!movie.adult) {
                                searchResults.add(movie);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();

                    if (searchResults.isEmpty()) {
                        emptyView.setText(R.string.NoResults);
                    }

                    onLoadSuccessful();
                } else {
                    // todo Error
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // todo Error
            }
        });
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setText(R.string.NoConnection);
    }

    public class SearchMovieAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
            return new Holder(new MovieViewList(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Movie movie = searchResults.get(position);

            MovieViewList view = (MovieViewList) holder.itemView;
            view.setPoster(movie.posterPath)
                .setTitle(movie.title)
                .setYear(movie.releaseDate)
                .setVoteAverage(movie.voteAverage)
                .setDivider(position != searchResults.size() - 1);
        }

        @Override
        public int getItemCount() {
            return searchResults != null ? searchResults.size() : 0;
        }
    }
}