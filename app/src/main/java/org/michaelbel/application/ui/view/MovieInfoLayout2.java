package org.michaelbel.application.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.application.R;
import org.michaelbel.application.moviemade.LayoutHelper;
import org.michaelbel.application.moviemade.Theme;
import org.michaelbel.application.rest.model.Crew;
import org.michaelbel.application.rest.model.Genre;
import org.michaelbel.application.rest.model.Movie;
import org.michaelbel.application.rest.model.Trailer;
import org.michaelbel.application.ui.adapter.Holder;
import org.michaelbel.application.ui.view.trailer.TrailerView;
import org.michaelbel.application.util.ScreenUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MovieInfoLayout2 extends LinearLayout {

    private TrailersSectionView trailersView;
    private TrailersAdapter trailersAdapter;
    private List<Trailer> trailersList = new ArrayList<>();

    private ImagesSectionView imagesView;

    private LinearLayout crewLayout;
    private TextView directorsTitleTextView;
    private TextView directorsTextView;
    private TextView writersTitleTextView;
    private TextView writersTextView;
    private TextView producersTitleTextView;
    private TextView producersTextView;

    private TextView originalTitleTextView;
    private TextView originalLangTextView;
    private TextView statusTextView;
    private TextView budgetTextView;
    private TextView revenueTextView;
    private TextView genresTextView;
    private TextView companiesTextView;

    private GenresSectionView chipsView;

    private LinearLayout linksLayout;
    private MoviePageView linkTmdbView;
    private MoviePageView linkImdbView;
    private MoviePageView linkHomeView;

    private Callback callback;
    private InfoMovieListener infoMovieListener;

    public interface InfoMovieListener {
        void onTrailersSectionClick(View view);
        void onTrailerClick(View view, String trailerKey);
        boolean onTrailerLongClick(View view, String trailerKey);
        void onMovieUrlClick(View view, int position);
    }

    public interface Callback {
        void onMovieLoaded();
    }

    public MovieInfoLayout2(Context context) {
        super(context);
        initialize(context);
    }

    public MovieInfoLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        setOrientation(VERTICAL);
        setBackgroundColor(ContextCompat.getColor(context, Theme.backgroundColor()));

//------IMAGES SECTION VIEW-------------------------------------------------------------------------

        imagesView = new ImagesSectionView(context);
        imagesView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        //addView(imagesView);

//------CREW VIEW--------------------------------------------------------------------------------------------

        crewLayout = new LinearLayout(context);
        crewLayout.setOrientation(VERTICAL);
        crewLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        crewLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(crewLayout);

        TextView crewTitle = new TextView(context);
        crewTitle.setLines(1);
        crewTitle.setMaxLines(1);
        crewTitle.setSingleLine();
        crewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        crewTitle.setText(context.getString(R.string.Crew));
        crewTitle.setGravity(Gravity.CENTER_VERTICAL);
        crewTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        crewTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        crewTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48,
                16, 0, 16, 0));
        crewLayout.addView(crewTitle);

        directorsTitleTextView = new TextView(context);
        directorsTitleTextView.setText(context.getString(R.string.Directors));
        directorsTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        directorsTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        crewLayout.addView(directorsTitleTextView);

        directorsTextView = new TextView(context);
        directorsTextView.setText("Loading...");
        directorsTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        directorsTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(directorsTextView);

        writersTitleTextView = new TextView(context);
        writersTitleTextView.setText(context.getString(R.string.Writers));
        writersTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        writersTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT,
                LayoutHelper.WRAP_CONTENT, 16, 0, 16, 0));
        crewLayout.addView(writersTitleTextView);

        writersTextView = new TextView(context);
        writersTextView.setText("Loading...");
        writersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        writersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(writersTextView);

        producersTitleTextView = new TextView(context);
        producersTitleTextView.setText(context.getString(R.string.Producers));
        producersTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        producersTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        crewLayout.addView(producersTitleTextView);

        producersTextView = new TextView(context);
        producersTextView.setText("Loading..");
        producersTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        producersTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 16));
        crewLayout.addView(producersTextView);

//------TRAILERS SECTION VIEW-----------------------------------------------------------------------

        trailersAdapter = new TrailersAdapter();

        trailersView = new TrailersSectionView(context);
        trailersView.setPadding(0, 0, 0, ScreenUtils.dp(4));
        trailersView.setAdapter(trailersAdapter);
        trailersView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        trailersView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onTrailersSectionClick(view);
            }
        });
        trailersView.setListener(new TrailersSectionView.SectionTrailersListener() {
            @Override
            public void onTrailerClick(View view, int position) {
                if (infoMovieListener != null) {
                    Trailer trailer = trailersList.get(position);
                    infoMovieListener.onTrailerClick(view, trailer.key);
                }
            }

            @Override
            public boolean onTrailerLongClick(View view, int position) {
                if (infoMovieListener != null) {
                    Trailer trailer = trailersList.get(position);
                    infoMovieListener.onTrailerLongClick(view, trailer.key);
                    return true;
                }
                return false;
            }
        });
        addView(trailersView);

//------INFO VIEW--------------------------------------------------------------------------------------------

        LinearLayout infoLayout = new LinearLayout(context);
        infoLayout.setOrientation(VERTICAL);
        infoLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        infoLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(infoLayout);

        TextView infoTitle = new TextView(context);
        infoTitle.setLines(1);
        infoTitle.setMaxLines(1);
        infoTitle.setSingleLine();
        infoTitle.setText(context.getString(R.string.Info));
        infoTitle.setGravity(Gravity.CENTER_VERTICAL);
        infoTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        infoTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        infoTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        infoTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48,
                16, 0, 16, 0));
        infoLayout.addView(infoTitle);

        TextView originalTitle = new TextView(context);
        originalTitle.setText(context.getString(R.string.OriginalTitle));
        originalTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalTitle);

        originalTitleTextView = new TextView(context);
        originalTitleTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalTitleTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalTitleTextView);

        TextView originalLang = new TextView(context);
        originalLang.setText(context.getString(R.string.OriginalLang));
        originalLang.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        originalLang.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(originalLang);

        originalLangTextView = new TextView(context);
        originalLangTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        originalLangTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(originalLangTextView);

        TextView statusTitle = new TextView(context);
        statusTitle.setText(context.getString(R.string.Status));
        statusTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        statusTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(statusTitle);

        statusTextView = new TextView(context);
        statusTextView.setText("Loading...");
        statusTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        statusTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(statusTextView);

        TextView budgetTitle = new TextView(context);
        budgetTitle.setText(context.getString(R.string.Budget));
        budgetTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        budgetTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(budgetTitle);

        budgetTextView = new TextView(context);
        budgetTextView.setText("Loading...");
        budgetTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        budgetTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(budgetTextView);

        TextView revenueTitle = new TextView(context);
        revenueTitle.setText(context.getString(R.string.Revenue));
        revenueTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        revenueTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(revenueTitle);

        revenueTextView = new TextView(context);
        revenueTextView.setText("Loading...");
        revenueTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        revenueTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(revenueTextView);

//--------------------------------------------------------------------------------------------------

        /*TextView genresTitle = new TextView(context);
        genresTitle.setText(context.getString(R.string.Genres));
        genresTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        genresTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(genresTitle);

        genresTextView = new TextView(context);
        genresTextView.setText("Loading...");
        genresTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        genresTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                16, 0, 16, 0));
        infoLayout.addView(genresTextView);*/

//--------------------------------------------------------------------------------------------------

        TextView companiesTitle = new TextView(context);
        companiesTitle.setText(context.getString(R.string.ProductionCompanies));
        companiesTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        companiesTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                16, 16, 16, 0));
        infoLayout.addView(companiesTitle);

        companiesTextView = new TextView(context);
        companiesTextView.setText("Loading...");
        companiesTextView.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        companiesTextView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT,
                LayoutHelper.WRAP_CONTENT, 16, 0, 16, 12));
        infoLayout.addView(companiesTextView);

//------GENRES--------------------------------------------------------------------------------------------

        LinearLayout genresLayout = new LinearLayout(context);
        genresLayout.setOrientation(VERTICAL);
        genresLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        genresLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT,
                0, 6, 0, 0));
        addView(genresLayout);

        TextView genresTitle = new TextView(context);
        genresTitle.setLines(1);
        genresTitle.setMaxLines(1);
        genresTitle.setSingleLine();
        genresTitle.setText(context.getString(R.string.Genres));
        genresTitle.setGravity(Gravity.CENTER_VERTICAL);
        genresTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        genresTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        genresTitle.setTextColor(ContextCompat.getColor(context, Theme.primaryTextColor()));
        genresTitle.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.WRAP_CONTENT, 48,
                16, 0, 16, 0));
        genresLayout.addView(genresTitle);

        chipsView = new GenresSectionView(context);
        chipsView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 16 - 2, 0, 16, 12));
        genresLayout.addView(chipsView);

//------WEB LINKS--------------------------------------------------------------------------------------------

        linksLayout = new LinearLayout(context);
        linksLayout.setOrientation(VERTICAL);
        linksLayout.setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));
        linksLayout.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 6, 0, 6));
        addView(linksLayout);

        linkTmdbView = new MoviePageView(context);
        linkTmdbView.setText(R.string.ViewOnTMDb);
        linkTmdbView.setDivider(true);
        linkTmdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkTmdbView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 1);
            }
        });
        linksLayout.addView(linkTmdbView);

        linkImdbView = new MoviePageView(context);
        linkImdbView.setText(R.string.ViewOnIMDb);
        linkImdbView.setDivider(true);
        linkImdbView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkImdbView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 2);
            }
        });
        linksLayout.addView(linkImdbView);

        linkHomeView = new MoviePageView(context);
        linkHomeView.setText(R.string.ViewHomepage);
        linkHomeView.setLayoutParams(LayoutHelper.makeLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        linkHomeView.setOnClickListener(view -> {
            if (infoMovieListener != null) {
                infoMovieListener.onMovieUrlClick(view, 3);
            }
        });
        linksLayout.addView(linkHomeView);
    }

//--------------------------------------------------------------------------------------------------

    public MovieInfoLayout2 setGenres(@NonNull List<Genre> genresList) {
        chipsView.setGenresList(genresList);
        return this;
    }

    public MovieInfoLayout2 setStatus(@NonNull String status) {
        statusTextView.setText(status.isEmpty() ? "-" : status);
        return this;
    }

    public MovieInfoLayout2 setBudget(int budget) {
        if (budget == 0) {
            // delete views
            budgetTextView.setText("-");
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
            budgetTextView.setText("$ " + formatter.format(budget));
        }
        return this;
    }

    public MovieInfoLayout2 setRevenue(int revenue) {
        if (revenue == 0) {
            // delete views
            revenueTextView.setText("-");
        } else {
            NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
            revenueTextView.setText("$ " + formatter.format(revenue));
        }
        return this;
    }

    public MovieInfoLayout2 setOriginalTitle(@NonNull String originalTitle) {
        originalTitleTextView.setText(originalTitle.isEmpty() ? "-" : originalTitle);
        return this;
    }

    public MovieInfoLayout2 setOriginalLang(@Nullable String originalLang) {
        if (originalLang != null) {
            originalLangTextView.setText(originalLang.isEmpty() ? "-" : originalLang);
        } else {
            originalLangTextView.setText("-");
        }

        return this;
    }

    public MovieInfoLayout2 setCompanies(@NonNull List<Movie.Companies> companiesList) {
        StringBuilder text = new StringBuilder();
        for (Movie.Companies company : companiesList) {
            text.append(company.name);
            if (company != companiesList.get(companiesList.size() - 1)) {
                text.append(", ");
            }
        }

        companiesTextView.setText(companiesList.isEmpty() ? "-" : text);
        return this;
    }

    public MovieInfoLayout2 setHomePage(@NonNull String homePage) {
        if (homePage.isEmpty()) {
            linkImdbView.setDivider(false);
            linksLayout.removeView(linkHomeView);
        }
        return this;
    }

    public MovieInfoLayout2 setCrew(@NonNull List<Crew> crewList) {
        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> producers = new ArrayList<>();

        for (Crew crew : crewList) {
            switch (crew.department) {
                case "Directing":
                    directors.add(crew.name);
                    break;
                case "Writing":
                    writers.add(crew.name);
                    break;
                case "Production":
                    producers.add(crew.name);
                    break;
            }
        }

        StringBuilder text1 = new StringBuilder();
        for (String director : directors) {
            text1.append(director);
            if (!Objects.equals(director, directors.get(directors.size() - 1))) {
                text1.append(", ");
            }
        }

        if (!text1.toString().isEmpty()) {
            directorsTextView.setText(text1.toString());
        } else {
            crewLayout.removeView(directorsTitleTextView);
            crewLayout.removeView(directorsTextView);
        }

        StringBuilder text2 = new StringBuilder();
        for (String writer : writers) {
            text2.append(writer);
            if (!Objects.equals(writer, writers.get(writers.size() - 1))) {
                text2.append(", ");
            }
        }

        if (!text2.toString().isEmpty()) {
            writersTextView.setText(text2.toString());
        } else {
            crewLayout.removeView(writersTitleTextView);
            crewLayout.removeView(writersTextView);
        }

        StringBuilder text3 = new StringBuilder();
        for (String producer : producers) {
            text3.append(producer);
            if (!Objects.equals(producer, producers.get(producers.size() - 1))) {
                text3.append(", ");
            }
        }

        if (!text3.toString().isEmpty()) {
            producersTextView.setText(text3.toString());
        } else {
            crewLayout.removeView(producersTitleTextView);
            crewLayout.removeView(producersTextView);
        }

        if (text1.toString().isEmpty() && text2.toString().isEmpty() && text3.toString().isEmpty()) {
            removeView(crewLayout);
        }
        return this;
    }

    public MovieInfoLayout2 setTrailers(@NonNull List<Trailer> list) {
        if (list.isEmpty()) {
            removeView(trailersView);
        } else {
            trailersList.addAll(list);
            trailersAdapter.notifyDataSetChanged();

            trailersView.setClickable(true);
            trailersView.getProgressBar().setVisibility(INVISIBLE);
        }

        return this;
    }

    public MovieInfoLayout2 setImages(@NonNull String poster, @NonNull String backdrop, int postersCount, int backdropsCount) {
        imagesView.setPoster(poster);
        imagesView.setBackdrop(backdrop);
        imagesView.setPostersCount(postersCount);
        imagesView.setBackdropsCount(backdropsCount);
        return this;
    }

    public void setListener(@NonNull InfoMovieListener listener) {
        infoMovieListener = listener;
    }

    public void setCallback(Callback listener) {
        callback = listener;
    }

    public class TrailersAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(new TrailerView(getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Trailer trailer = trailersList.get(position);

            TrailerView view = (TrailerView) holder.itemView;
            view.setTitle(trailer.name)
                .setQuality(trailer.size)
                .setSite(trailer.site)
                .setTrailerImage(trailer.key);

            if (position == 0) {
                view.changeLayoutParams(true);
            }

            if (position == trailersList.size() - 1) {
                view.changeLayoutParams(false);
            }
        }

        @Override
        public int getItemCount() {
            return trailersList != null ? trailersList.size() : 0;
        }
    }
}