package org.michaelbel.application.moviemade.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;

@SuppressWarnings("all")
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