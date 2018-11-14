package org.michaelbel.moviemade.ui.modules.trailers;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Video;

import java.util.ArrayList;
import java.util.List;

public interface TrailersMvp extends MvpView {

    void setTrailers(List<Video> trailers);

    void setError(@EmptyViewMode int mode);
}