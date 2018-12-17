package org.michaelbel.moviemade.data.eventbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxBus2 {

    public RxBus2() {}

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}