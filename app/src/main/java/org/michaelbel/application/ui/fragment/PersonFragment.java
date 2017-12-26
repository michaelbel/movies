package org.michaelbel.application.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.PEOPLE;
import org.michaelbel.application.rest.model.Person;
import org.michaelbel.application.ui.PersonActivity;
import org.michaelbel.application.ui.view.EmptyView;
import org.michaelbel.application.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonFragment extends Fragment {

    private int personId;
    private String personName;

    private Person currentPerson;
    private PersonActivity activity;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private NestedScrollView nestedScrollView;

    private ImageView posterImageView;
    private TextView bornTextView;
    private TextView bioTextView;

    public static PersonFragment newInstance(int personId, String personName) {
        Bundle args = new Bundle();
        args.putInt("personId", personId);
        args.putString("personName", personName);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (PersonActivity) getActivity();
        setHasOptionsMenu(true);

        View fragmentView = inflater.inflate(R.layout.fragment_person, container, false);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        if (getArguments() != null) {
            personId = getArguments().getInt("personId");
            personName = getArguments().getString("personName");
        }

        FrameLayout topLayout = fragmentView.findViewById(R.id.top_layout);
        topLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));

        posterImageView = new ImageView(activity);
        posterImageView.setScaleType(ImageView.ScaleType.CENTER);
        posterImageView.setImageResource(R.drawable.movie_placeholder);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(110, 180,
                Gravity.START | Gravity.TOP, 16, 16, 0, 16));
        topLayout.addView(posterImageView);

        LinearLayout bornLayout = new LinearLayout(activity);
        bornLayout.setOrientation(LinearLayout.VERTICAL);
        bornLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        bornLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                Gravity.BOTTOM, 110 + 16 + 16, 0, 16, 16));
        topLayout.addView(bornLayout);

        TextView bornTitle = new TextView(activity);
        bornTitle.setText("Born");
        bornTitle.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        bornTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        bornLayout.addView(bornTitle);

        bornTextView = new TextView(activity);
        bornTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        bornTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));
        bornLayout.addView(bornTextView);

        LinearLayout bioLayout = fragmentView.findViewById(R.id.bio_layout);
        bioLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));

        bioTextView = new TextView(activity);
        bioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        bioTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        bioTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        bioTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        bioLayout.addView(bioTextView);

        nestedScrollView = fragmentView.findViewById(R.id.nested_scroll);
        nestedScrollView.setVisibility(View.INVISIBLE);

        progressBar = fragmentView.findViewById(R.id.progress_bar);

        emptyView = fragmentView.findViewById(R.id.empty_view);
        emptyView.setVisibility(View.INVISIBLE);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            progressBar.setVisibility(View.INVISIBLE);
            nestedScrollView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            loadPersonDetails(personId);
        }
    }

    private void loadPersonDetails(int personId) {
        PEOPLE service = ApiFactory.getRetrofit().create(PEOPLE.class);
        Call<Person> call = service.getDetails(personId, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful()) {
                    currentPerson = response.body();
                    startPerson(currentPerson);
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                onLoadError();
            }
        });
    }

    private void startPerson(Person person) {
        Glide.with(activity)
                .load("http://image.tmdb.org/t/p/w500/" + person.profilePath)
                .into(posterImageView);

        bornTextView.setText(person.birthday + ", " + person.birthPlace);
        bioTextView.setText(person.bio);

        onLoadSuccessful();
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        nestedScrollView.setVisibility(View.VISIBLE);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        nestedScrollView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(R.string.NoConnection);
    }
}