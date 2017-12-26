package org.michaelbel.application.moviemade.eventbus;

@SuppressWarnings("all")
public class Events {

    public static class Listener {

        public CharSequence query;

        public Listener(CharSequence query) {
            this.query = query;
        }
    }
}