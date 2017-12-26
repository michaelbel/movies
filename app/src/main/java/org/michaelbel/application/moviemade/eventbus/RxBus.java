package org.michaelbel.application.moviemade.eventbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

@SuppressWarnings("all")
public class RxBus {

    private static volatile RxBus instance;

    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }
}