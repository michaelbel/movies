package org.michaelbel.moviemade.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import org.michaelbel.moviemade.PersonActivity;
import org.michaelbel.moviemade.R;
import org.michaelbel.moviemade.app.LayoutHelper;
import org.michaelbel.moviemade.app.Theme;
import org.michaelbel.moviemade.app.Url;
import org.michaelbel.moviemade.app.browser.Browser;
import org.michaelbel.moviemade.mvp.presenter.PersonPresenter;
import org.michaelbel.moviemade.mvp.view.MvpPersonView;
import org.michaelbel.moviemade.rest.model.Cast;
import org.michaelbel.moviemade.rest.model.Person;
import org.michaelbel.moviemade.ui.interfaces.PersonViewListener;
import org.michaelbel.moviemade.ui.view.EmptyView;
import org.michaelbel.moviemade.ui.view.PersonViewLayout;
import org.michaelbel.moviemade.util.AndroidUtils;

import java.util.Locale;

public class PersonFragment extends MvpAppCompatFragment implements MvpPersonView, PersonViewListener {

    @InjectPresenter
    public PersonPresenter presenter;

    private Cast extraPerson;
    private Person loadedPerson;

    private PersonActivity activity;

    private EmptyView emptyView;
    private ProgressBar progressBar;
    private PersonViewLayout personView;
    private ScrollView scrollView;
    private SwipeRefreshLayout fragmentView;

    public static PersonFragment newInstance(Cast person) {
        Bundle args = new Bundle();
        args.putSerializable("person", person);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity = (PersonActivity) getActivity();

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
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });

        fragmentView = new SwipeRefreshLayout(activity);
        fragmentView.setRefreshing(false);
        fragmentView.setColorSchemeResources(Theme.accentColor());
        fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        fragmentView.setOnRefreshListener(() -> {
            fragmentView.setRefreshing(false);
        });

        FrameLayout contentLayout = new FrameLayout(activity);
        fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(activity);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        scrollView = new ScrollView(activity);
        scrollView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        contentLayout.addView(scrollView);

        personView = new PersonViewLayout(activity);
        personView.addListener(this);
        personView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        scrollView.addView(personView);

        return contentLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            extraPerson = (Cast) getArguments().getSerializable("person");
        }

        presenter.loadPerson(extraPerson.id);
    }

    @Override
    public void showPerson(Person person) {
        loadedPerson = person;

        personView.addProfileImage(person.profilePath);
        personView.addName(person.name);
        personView.addPopularity(String.valueOf(person.popularity));
        personView.addBio(person.bio);
        personView.addOtherNames("Also known as: " + AndroidUtils.formatNames(person.names));
        personView.addImdb(person.imdbId);
        personView.addHomepage(person.homepage);
        personView.addBirthPlace(getString(R.string.BirthPlace, person.birthPlace));
        personView.addBirthday(getString(R.string.Birthday, person.birthday));
        personView.addDeathday(getString(R.string.Deathday, person.deathday));

        //personView.addCareer("Actor, Writing, Crew");
    }

    @Override
    public void showError() {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(EmptyView.MODE_NO_CONNECTION);
        progressBar.setVisibility(View.GONE);
        fragmentView.setRefreshing(false);
    }

    @Override
    public void showComplete() {
        progressBar.setVisibility(View.GONE);
        fragmentView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onWebpageClick(View view, int position) {
        if (position == 0) {
            Browser.openUrl(activity, String.format(Locale.US, Url.TMDB_PERSON, extraPerson.id));
        } else if (position == 1) {
            // todo: Browser.openUrl(activity, String.format(Locale.US, Url.IMDB_MOVIE, loadedPerson.imdbId));
        } else if (position == 2) {
            Browser.openUrl(activity, loadedPerson.homepage);
        }
    }
}