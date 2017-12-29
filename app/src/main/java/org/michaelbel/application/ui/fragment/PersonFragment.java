package org.michaelbel.application.ui.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.ApiFactory;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.moviemade.Url;
import org.michaelbel.application.rest.api.PEOPLE;
import org.michaelbel.application.rest.model.Cast;
import org.michaelbel.application.rest.model.Person;
import org.michaelbel.application.ui.PersonActivity;
import org.michaelbel.application.util.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//@SuppressWarnings("all")
public class PersonFragment extends Fragment {

    private Cast currentPerson;
    private Person loadedPerson;

    private PersonActivity activity;

    private TextView emptyView;
    private ProgressBar progressBar;
    private ImageView posterImageView;
    private TextView bornTextView;
    private TextView bioTextView;

    public static PersonFragment newInstance(Cast person) {
        Bundle args = new Bundle();
        args.putSerializable("person", person);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (PersonActivity) getActivity();
        setHasOptionsMenu(true);

        FrameLayout fragmentView = new FrameLayout(activity);
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        fragmentView.addView(progressBar);

        emptyView = new TextView(activity);
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        fragmentView.addView(emptyView);

        ScrollView scrollView = new ScrollView(activity);
        scrollView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        fragmentView.addView(scrollView);

        LinearLayout contentLayout = new LinearLayout(activity);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(contentLayout);

        FrameLayout topLayout = new FrameLayout(activity);
        topLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        contentLayout.addView(topLayout);

        posterImageView = new ImageView(activity);
        posterImageView.setScaleType(ImageView.ScaleType.CENTER);
        posterImageView.setImageResource(R.drawable.movie_placeholder);
        posterImageView.setLayoutParams(LayoutHelper.makeFrame(110, 180, Gravity.START | Gravity.TOP, 16, 16, 0, 16));
        topLayout.addView(posterImageView);

        LinearLayout bornLayout = new LinearLayout(activity);
        bornLayout.setOrientation(LinearLayout.VERTICAL);
        bornLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        bornLayout.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.BOTTOM, 110 + 16 + 16, 0, 16, 16));
        topLayout.addView(bornLayout);

        TextView bornTitle = new TextView(activity);
        bornTitle.setText("Born");
        bornTitle.setTextColor(ContextCompat.getColor(activity, Theme.primaryTextColor()));
        bornTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT));
        bornLayout.addView(bornTitle);

        bornTextView = new TextView(activity);
        bornTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        bornTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0));
        bornLayout.addView(bornTextView);

        LinearLayout bioLayout = new LinearLayout(activity);
        bioLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.foregroundColor()));
        contentLayout.addView(bioLayout);

        bioTextView = new TextView(activity);
        bioTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        bioTextView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        bioTextView.setTextColor(ContextCompat.getColor(activity, Theme.secondaryTextColor()));
        bioTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16, 16, 16, 16));
        bioLayout.addView(bioTextView);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            currentPerson = (Cast) getArguments().getSerializable("person");
        }

        if (NetworkUtils.getNetworkStatus() == NetworkUtils.TYPE_NOT_CONNECTED) {
            progressBar.setVisibility(View.INVISIBLE);
            //nestedScrollView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            loadPersonDetails();
        }
    }

    private void loadPersonDetails() {
        PEOPLE service = ApiFactory.getRetrofit().create(PEOPLE.class);
        Call<Person> call = service.getDetails(currentPerson.id, Url.TMDB_API_KEY, Url.en_US, null);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(@NonNull Call<Person> call, @NonNull Response<Person> response) {
                if (response.isSuccessful()) {
                    loadedPerson = response.body();
                    startPerson();
                } else {
                    onLoadError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Person> call, @NonNull Throwable t) {
                onLoadError();
            }
        });
    }

    private void startPerson() {
        Picasso.with(activity)
               .load(Url.getImage(currentPerson.profilePath, "w500"))
               .placeholder(R.drawable.people_placeholder)
               .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
               .into(posterImageView);

        bornTextView.setText(loadedPerson.birthday + ", " + loadedPerson.birthPlace);
        bioTextView.setText(loadedPerson.bio);

        onLoadSuccessful();
    }

    private void onLoadSuccessful() {
        progressBar.setVisibility(View.INVISIBLE);
        //nestedScrollView.setVisibility(View.VISIBLE);
    }

    private void onLoadError() {
        progressBar.setVisibility(View.INVISIBLE);
        //nestedScrollView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setText(R.string.NoConnection);
    }
}