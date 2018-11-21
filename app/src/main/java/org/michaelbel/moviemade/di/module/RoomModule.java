package org.michaelbel.moviemade.di.module;

import android.content.Context;

import dagger.Module;

@Module
public class RoomModule {

    //private static final String ROOM_DATABASE_NAME = "moviesDb_Dev";

    private Context context;

    public RoomModule(Context context) {
        this.context = context;
    }

    /*@NonNull
    @Provides
    @Singleton
    MoviesDatabase provideRoomDatabase() {
        return Room.databaseBuilder(context, MoviesDatabase.class, ROOM_DATABASE_NAME).build();
    }*/
}