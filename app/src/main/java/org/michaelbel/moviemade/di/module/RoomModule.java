package org.michaelbel.moviemade.di.module;

import android.content.Context;

import org.michaelbel.moviemade.room.database.MoviesDatabase;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private static final String ROOM_DATABASE_NAME = "moviesDb_Dev";

    private Context context;

    public RoomModule(Context context) {
        this.context = context;
    }

    @NonNull
    @Provides
    @Singleton
    MoviesDatabase provideRoomDatabase() {
        return Room.databaseBuilder(context, MoviesDatabase.class, ROOM_DATABASE_NAME).build();
    }
}