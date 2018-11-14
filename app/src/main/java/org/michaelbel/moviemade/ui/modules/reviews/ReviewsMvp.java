package org.michaelbel.moviemade.ui.modules.reviews;

import com.arellomobile.mvp.MvpView;

import org.michaelbel.moviemade.annotation.EmptyViewMode;
import org.michaelbel.moviemade.data.dao.Review;

import java.util.List;

public interface ReviewsMvp extends MvpView {

    void setReviews(List<Review> reviews, boolean firstPage);

    void showError(@EmptyViewMode int mode);
}