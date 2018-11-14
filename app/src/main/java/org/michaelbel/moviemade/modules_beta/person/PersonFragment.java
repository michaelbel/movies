package org.michaelbel.moviemade.modules_beta.person;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.material.tabs.TabLayout;

import org.michaelbel.moviemade.ConstantsKt;
import org.michaelbel.moviemade.LayoutHelper;
import org.michaelbel.moviemade.Theme;
import org.michaelbel.moviemade.browser.Browser;
import org.michaelbel.moviemade.data.dao.Cast;
import org.michaelbel.moviemade.data.dao.Person;
import org.michaelbel.moviemade.modules_beta.view.PersonViewLayout;
import org.michaelbel.moviemade.moxy.MvpAppCompatFragment;
import org.michaelbel.moviemade.ui.widgets.EmptyView;
import org.michaelbel.moviemade.utils.AndroidUtils;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class PersonFragment extends MvpAppCompatFragment implements PersonMvp, PersonViewListener {

    private Cast extraCastPerson;
    private Person extraPeoplePerson;
    private Person loadedPerson;

    private int id;

    private PersonActivity activity;

    private EmptyView emptyView;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private PersonViewLayout personView;
    //private SwipeRefreshLayout fragmentView;

    @InjectPresenter
    public PersonPresenter presenter;

    public static PersonFragment newInstance(Cast person) {
        Bundle args = new Bundle();
        args.putSerializable("cast_person", person);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PersonFragment newInstance(Person person) {
        Bundle args = new Bundle();
       // args.putParcelable("people_person", person);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (PersonActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle args) {
        activity.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {}

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (AndroidUtils.scrollToTop()) {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });

        //fragmentView = new SwipeRefreshLayout(activity);
        //fragmentView.setRefreshing(false);
        //fragmentView.setColorSchemeResources(Theme.accentColor());
        //fragmentView.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        //fragmentView.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        //fragmentView.setOnRefreshListener(() -> {
        //    fragmentView.setRefreshing(false);
        //});

        FrameLayout contentLayout = new FrameLayout(activity);
        contentLayout.setBackgroundColor(ContextCompat.getColor(activity, Theme.backgroundColor()));
        //contentLayout.setLayoutParams(LayoutHelper.makeSwipeRefresh(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        //fragmentView.addView(contentLayout);

        progressBar = new ProgressBar(activity);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, Theme.accentColor()), PorterDuff.Mode.MULTIPLY);
        progressBar.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));
        contentLayout.addView(progressBar);

        emptyView = new EmptyView(activity);
        emptyView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 24, 0, 24, 0));
        contentLayout.addView(emptyView);

        scrollView = new ScrollView(activity);
        scrollView.setVerticalScrollBarEnabled(AndroidUtils.scrollbars());
        scrollView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        contentLayout.addView(scrollView);

        personView = new PersonViewLayout(activity);
        personView.addListener(this);
        personView.setLayoutParams(LayoutHelper.makeFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));
        scrollView.addView(personView);

        return contentLayout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() == null) {
            return;
        }

        extraCastPerson = (Cast) getArguments().getSerializable("cast_person");
        extraPeoplePerson = getArguments().getParcelable("people_person");

        if (extraCastPerson != null) {
            id = extraCastPerson.getId();
        } else if (extraPeoplePerson != null) {
          //  id = extraPeoplePerson.id;
        }

        presenter.loadPerson(id);
    }

    @Override
    public void showPerson(Person person) {
        loadedPerson = person;

        personView.addProfileImage(person.getProfilePath());
        personView.addName(person.getName());
        personView.addPopularity(String.valueOf(person.getPopularity()));
        personView.addBio(person.getBiography());
        personView.addOtherNames(AndroidUtils.formatNames(person.getAlso_known_as()));
        personView.addImdb(person.getImdb_id());
        personView.addHomepage(person.getHomepage());
        personView.addBirthPlace(person.getPlaceOfBirth());
        personView.setDates(person.getBirthDay(), person.getDeathDay());

        personView.loadedSuccess();
        //personView.addCareer("Actor, Writing, Crew");

        //fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void showError(int mode) {
        //fragmentView.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setMode(mode);
    }

    @Override
    public void onBirthPlaceClick(View view) {
        Uri uri = Uri.parse(String.format("geo:0,0?q=%s", loadedPerson.getPlaceOfBirth()));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onBirthPlaceLongClick(View view) {
        /*BottomSheet.Builder builder = new BottomSheet.Builder(activity);
        builder.setCellHeight(ScreenUtils.dp(52));
        builder.setIconColorRes(Theme.iconActiveColor());
        builder.setItemTextColorRes(Theme.primaryTextColor());
        builder.setBackgroundColorRes(Theme.foregroundColor());
        builder.setItems(new int[] { R.string.OpenAddress, R.string.Copy }, (dialogInterface, i) -> {
            if (i == 0) {
                personView.getBirthPlaceLayout().performClick();
            } else if (i == 1) {
                Extensions.copyToClipboard(activity, loadedPerson.birthPlace);
                Toast.makeText(activity, getString(R.string.ClipboardCopied, getString(R.string.Address)), Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();*/
    }

    @Override
    public void onWebpageClick(View view, int position) {
        if (position == 0) {
            Browser.openUrl(activity, String.format(Locale.US, ConstantsKt.TMDB_PERSON, id));
        } else if (position == 1) {
            Browser.openUrl(activity, String.format(Locale.US, ConstantsKt.IMDB_PERSON, loadedPerson.getImdb_id()));
        } else if (position == 2) {
            Browser.openUrl(activity, loadedPerson.getHomepage());
        }
    }
}