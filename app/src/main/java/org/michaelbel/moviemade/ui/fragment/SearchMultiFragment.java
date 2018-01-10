package org.michaelbel.moviemade.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.SearchActivity;
import org.michaelbel.moviemade.app.ApiFactory;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.model.SearchItem;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.model.People;
import org.michaelbel.moviemade.ui.adapter.SearchPeopleAdapter;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.widget.RecyclerListView;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.DateUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;

import static android.app.Activity.RESULT_OK;

public class SearchMultiFragment extends Fragment {

    private final int SPEECH_REQUEST_CODE = 10;

    private final int MODE_ACTION_CLEAR = 1;
    private final int MODE_ACTION_VOICE = 2;

    private String readyQuery;
    private int iconActionMode;

    private Menu actionMenu;
    private SearchActivity activity;
    private SearchPeopleAdapter adapter;
    private List<People> searches = new ArrayList<>();

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private RecyclerListView recyclerView;

    public static SearchMultiFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString("query", query);

        SearchMultiFragment fragment = new SearchMultiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        actionMenu = menu;
        actionMenu.add(null)
                  .setIcon(R.drawable.ic_voice)
                  .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM)
                  .setOnMenuItemClickListener(item -> {
                      if (iconActionMode == MODE_ACTION_VOICE) {
                          Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                          intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                          intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
                          intent.putExtra(RecognizerIntent.EXTRA_PROMPT, R.string.SpeakNow);
                          startActivityForResult(intent, SPEECH_REQUEST_CODE);
                      } else {
                          activity.searchEditText.getText().clear();
                          changeActionIcon();

                          InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                          imm.showSoftInput(activity.searchEditText, InputMethodManager.SHOW_IMPLICIT);
                      }
                      return true;
                  });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (SearchActivity) getActivity();
        activity.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });

        iconActionMode = MODE_ACTION_VOICE;

        activity.searchEditText.setOnEditorActionListener((textView, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {
                search(textView.getText().toString().trim());
                AndroidUtils.hideKeyboard(activity.searchEditText);
                return true;
            }
            return false;
        });
        activity.searchEditText.addTextChangedListener(new TextWatcher() {
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

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        emptyView = new EmptyView(activity);
        emptyView.setMode(EmptyView.MODE_NO_RESULTS);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        adapter = new SearchPeopleAdapter(searches);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        recyclerView.setOnItemClickListener((view1, position) -> {
            People people = searches.get(position);
            //activity.startMovie(movie);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //AndroidUtils.hideKeyboard(activity.searchEditText);
            }
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            readyQuery = getArguments().getString("query");
        }

        if (readyQuery == null) {
            //InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.showSoftInput(activity.searchEditText, InputMethodManager.SHOW_IMPLICIT);
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());
        } else {
            activity.searchEditText.setText(readyQuery);
            activity.searchEditText.setSelection(activity.searchEditText.getText().length());

            iconActionMode = MODE_ACTION_CLEAR;
            actionMenu.getItem(0).setIcon(Theme.getIcon(R.drawable.ic_clear, ContextCompat.getColor(activity, Theme.primaryTextColor())));

            search(readyQuery);

            AndroidUtils.hideKeyboard(activity.searchEditText);
        }
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
                        if (activity.searchEditText != null) {
                            activity.searchEditText.setText(textResults);
                            activity.searchEditText.setSelection(activity.searchEditText.getText().length());
                            search(textResults);
                            changeActionIcon();
                        }
                    }
                }
            }
        }
    }

    private void changeActionIcon() {
        if (actionMenu != null) {
            if (activity.searchEditText.getText().toString().trim().isEmpty()) {
                iconActionMode = MODE_ACTION_VOICE;
                actionMenu.getItem(0).setIcon(Theme.getIcon(R.drawable.ic_voice, ContextCompat.getColor(activity, Theme.primaryTextColor())));
            } else {
                iconActionMode = MODE_ACTION_CLEAR;
                actionMenu.getItem(0).setIcon(Theme.getIcon(R.drawable.ic_clear, ContextCompat.getColor(activity, Theme.primaryTextColor())));
            }
        }
    }

    private void search(String query) {
        searches.clear();
        emptyView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        if (NetworkUtils.notConnected()) {
            onLoadError();
        } else {
            performSearch(query);
        }
    }

    private void performSearch(String query) {
        SEARCH service = ApiFactory.getRetrofit().create(SEARCH.class);
        //Call<PeopleResponce> call = service.searchPeople();
        //call.enqueue();

        /*Call<MovieResponse> call = service.searchMovies(Url.SEARCH_FOR_MOVIES, Url.TMDB_API_KEY, Url.en_US, query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<Movie> newMovies = new ArrayList<>();

                    if (AndroidUtils.includeAdult()) {
                        newMovies.addAll(response.body().movies);
                    } else {
                        for (Movie movie : response.body().movies) {
                            if (!movie.adult) {
                                newMovies.add(movie);
                            }
                        }
                    }

                    //searches.addAll(newMovies);
                    //adapter.notifyItemRangeInserted(searches.size() + 1, newMovies.size());

                    if (searches.isEmpty()) {
                        emptyView.setMode(EmptyView.MODE_NO_RESULTS);
                    }

                    addQueryToHistory(query);
                    onLoadSuccessful();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                onLoadError();
            }
        });*/
    }

    private void addQueryToHistory(String query) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                SearchItem item = realm.createObject(SearchItem.class);
                item.queryTitle = query;
                item.queryDate = DateUtils.getCurrentDateAndTime();
            }
        });
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
    }
}