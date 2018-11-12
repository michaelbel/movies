package org.michaelbel.moviemade.ui.modules.trailers;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.rest.model.v3.Trailer;

import java.util.ArrayList;
import java.util.List;

public interface TrailersMvp extends MvpView {

    void setTrailers(ArrayList<Trailer> trailers);

    void showError();
}