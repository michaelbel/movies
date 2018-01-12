package org.michaelbel.moviemade.mvp.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import org.michaelbel.moviemade.app.ApiFactory;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.mvp.view.MvpPersonView;
import org.michaelbel.moviemade.rest.api.PEOPLE;
import org.michaelbel.moviemade.rest.model.Person;
import org.michaelbel.moviemade.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@InjectViewState
public class PersonPresenter extends MvpPresenter<MvpPersonView> {

    public void loadPerson(int personId) {
        if (NetworkUtils.notConnected()) {
            getViewState().showError();
        } else {
            loadPersonDetails(personId);
        }
    }

    private void loadPersonDetails(int personId) {
        PEOPLE service = ApiFactory.getRetrofit().create(PEOPLE.class);
        Call<Person> call = service.getDetails(personId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                if (!response.isSuccessful()) {
                    getViewState().showError();
                    return;
                }

                getViewState().showPerson(response.body());
                getViewState().showComplete();
            }

            @Override
            public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                getViewState().showError();
            }
        });
    }
}