package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.model.SearchItem;
import org.michaelbel.moviemade.mvp.view.MvpSearchView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.TmdbObject;
import org.michaelbel.moviemade.rest.api.SEARCH;
import org.michaelbel.moviemade.rest.response.CollectionResponse;
import org.michaelbel.moviemade.rest.response.KeywordsResponse;
import org.michaelbel.moviemade.util.DateUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class SearchCollectionsPresenter extends MvpPresenter<MvpSearchView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    private String currentQuery;

    public void search(String query) {
        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;
        currentQuery = query;

        getViewState().searchStart();

        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        SEARCH service = ApiFactory.createService(SEARCH.class);
        service.searchCollections(Url.TMDB_API_KEY, Url.en_US, query, page).enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(@NonNull Call<CollectionResponse> call, @NonNull Response<CollectionResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }

                //addToSearchHistory(query);

                totalPages = response.body().totalPages;

                List<TmdbObject> results = new ArrayList<>();
                results.addAll(response.body().collections);

                if (results.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_RESULTS);
                    return;
                }

                getViewState().searchComplete(results, response.body().totalResults);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<CollectionResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadResults() {
        SEARCH service = ApiFactory.createService(SEARCH.class);
        service.searchCollections(Url.TMDB_API_KEY, currentQuery, Url.en_US, page).enqueue(new Callback<CollectionResponse>() {
            @Override
            public void onResponse(@NonNull Call<CollectionResponse> call, @NonNull Response<CollectionResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
                }

                List<TmdbObject> results = new ArrayList<>();
                results.addAll(response.body().collections);

                if (results.isEmpty()) {
                    return;
                }

                getViewState().nextPageLoaded(results);
                page++;
                loading = false;
            }

            @Override
            public void onFailure(@NonNull Call<CollectionResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
            }
        });

        service.searchKeywords(Url.TMDB_API_KEY, currentQuery, page).enqueue(new Callback<KeywordsResponse>() {
            @Override
            public void onResponse(@NonNull Call<KeywordsResponse> call, @NonNull Response<KeywordsResponse> response) {

            }

            @Override
            public void onFailure(@NonNull Call<KeywordsResponse> call, @NonNull Throwable t) {

            }
        });

        loading = true;
    }

    private void addToSearchHistory(String query) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            SearchItem item = realm1.createObject(SearchItem.class);
            item.queryTitle = query;
            item.queryDate = DateUtils.getCurrentDateAndTimeWithMilliseconds();
        });
    }
}