package org.michaelbel.application.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.MOVIES;
import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.response.CreditResponse;
import org.michaelbel.application.ui.MovieActivity;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.CastView;
import org.michaelbel.application.ui.view.widget.RecyclerListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("all")
public class CastMovieFragment extends Fragment {

    private int movieId;

    private CastAdapter adapter;
    private MovieActivity activity;
    private LinearLayoutManager layoutManager;
    private List<Cast> list = new ArrayList<>();

    private RecyclerListView recyclerView;

    public static CastMovieFragment newInstance(int movieId) {
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);

        CastMovieFragment fragment = new CastMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MovieActivity) getActivity();

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        adapter = new CastAdapter();
        layoutManager = new LinearLayoutManager(activity);

        recyclerView = new RecyclerListView(activity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        /*recyclerView.setOnItemClickListener((view1, position) -> {
            Cast cast = list.getTypeface(position);
            activity.startPerson(cast.id, cast.name);
        });*/
        recyclerView.setOnItemLongClickListener((view, position) -> {

            return true;
        });
        fragmentView.addView(recyclerView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt("movieId");
        }

        loadCredits();
    }

    private void loadCredits() {
        MOVIES service = ApiFactory.getRetrofit().create(MOVIES.class);
        Call<CreditResponse> call = service.getCredits(movieId, Url.TMDB_API_KEY);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.isSuccessful()) {
                    if (!list.isEmpty()) {
                        list.clear();
                    }
                    list.addAll(response.body().castList);
                    adapter.notifyDataSetChanged();

                    if (list.isEmpty()) {

                    }
                } else {
                    //FirebaseCrash.report(new Error("Server not found"));
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                //FirebaseCrash.report(t);
            }
        });
    }

    private class CastAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new CastView(activity));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Cast cast = list.get(position);

            CastView view = (CastView) holder.itemView;
            view.setName(cast.name)
                .setCharacter(cast.character)
                .setProfileImage(cast.profilePath)
                .setDivider(position != list.size() - 1);
        }

        @Override
        public int getItemCount() {
            return list != null ? list.size() : 0;
        }
    }
}