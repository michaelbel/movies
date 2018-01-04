package org.michaelbel.application.ui.mvp;

@SuppressWarnings("all")
public class BasePresenter<V extends BaseActivityModel> implements MvpPresenter<V> {

    private V mvpView;

    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        mvpView = null;
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {

        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to the Presenter");
        }
    }
}