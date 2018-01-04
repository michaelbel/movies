package org.michaelbel.application.ui.mvp;

public interface MvpPresenter<V extends BaseActivityModel> {

    void attachView(V mvpView);

    void detachView();
}