package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.annotation.EmptyViewMode;
import org.michaelbel.moviemade.mvp.view.MvpPopularPeopleView;
import org.michaelbel.moviemade.rest.ApiFactory;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.People;
import org.michaelbel.moviemade.rest.response.PeopleResponse;
import org.michaelbel.moviemade.util.AndroidUtils;
import org.michaelbel.moviemade.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PopularPeoplePresenter extends MvpPresenter<MvpPopularPeopleView> {

    public int page;
    public int totalPages;
    public boolean loading;
    public boolean loadingLocked;

    public void loadPeople() {
        if (NetworkUtils.notConnected()) {
            getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            return;
        }

        page = 1;
        totalPages = 0;
        loading = false;
        loadingLocked = false;

        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Call<PeopleResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(@NonNull Call<PeopleResponse> call, @NonNull Response<PeopleResponse> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                if (response.body() == null) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                totalPages = response.body().totalPages;

                List<People> newPeople = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newPeople.addAll(response.body().people);
                } else {
                    for (People people : response.body().people) {
                        if (!people.adult) {
                            newPeople.add(people);
                        }
                    }
                }

                if (newPeople.isEmpty()) {
                    getViewState().showError(EmptyViewMode.MODE_NO_PEOPLE);
                    return;
                }

                getViewState().showResults(newPeople);
                page++;
            }

            @Override
            public void onFailure(@NonNull Call<PeopleResponse> call, @NonNull Throwable t) {
                getViewState().showError(EmptyViewMode.MODE_NO_CONNECTION);
            }
        });
    }

    public void loadResults() {
        PEOPLE service = ApiFactory.createService(PEOPLE.class);
        Call<PeopleResponse> call = service.getPopular(Url.TMDB_API_KEY, Url.en_US, page);
        call.enqueue(new Callback<PeopleResponse>() {
            @Override
            public void onResponse(@NonNull Call<PeopleResponse> call, @NonNull Response<PeopleResponse> response) {
                if (!response.isSuccessful()) {
                    loadingLocked = true;
                    return;
                }

                List<People> newPeople = new ArrayList<>();

                if (AndroidUtils.includeAdult()) {
                    newPeople.addAll(response.body().people);
                } else {
                    for (People people : response.body().people) {
                        if (!people.adult) {
                            newPeople.add(people);
                        }
                    }
                }

                if (newPeople.isEmpty()) {
                    return;
                }

                getViewState().showResults(newPeople);
                page++;
                loading = false;
            }

            @Override
            public void onFailure(@NonNull Call<PeopleResponse> call, @NonNull Throwable t) {
                loadingLocked = true;
                loading = false;
            }
        });

        loading = true;
    }
}