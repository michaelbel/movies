package org.michaelbel.moviemade.trailers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.michaelbel.moviemade.Moviemade;
import org.michaelbel.moviemade.data.dao.Movie;
import org.michaelbel.moviemade.data.dao.Video;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersActivity;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersMvp;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersMvp$$State;
import org.michaelbel.moviemade.ui.modules.trailers.TrailersPresenter;
import org.michaelbel.moviemade.utils.EmptyViewMode;
import org.michaelbel.moviemade.utils.IntentsKt;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrailersActivityTest {

    @Mock
    TrailersMvp trailersMvp;

    @Mock
    TrailersMvp$$State trailersMvpState;

    private Moviemade moviemade;
    private TrailersPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TrailersPresenter();
        presenter.attachView(trailersMvp);
        presenter.setViewState(trailersMvpState);
        moviemade = new Moviemade();
    }

    @Test
    public void details_shouldShowDetailsContainer() {
        List<Video> videos = new ArrayList<>();
        presenter.getVideos(1124);

        verify(trailersMvpState).setError(EmptyViewMode.MODE_NO_CONNECTION);
        verify(trailersMvpState).setTrailers(videos);
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}