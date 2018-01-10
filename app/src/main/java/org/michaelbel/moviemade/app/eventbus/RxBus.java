package org.michaelbel.moviemade.app.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;

public class RxBus {

    public RxBus() {}

    private PublishSubject<Object> publishSubject = PublishSubject.create();

    public void send(Object object) {
        publishSubject.onNext(object);
    }

    public Observable <Object> toObservable() {
        return publishSubject;
    }
}