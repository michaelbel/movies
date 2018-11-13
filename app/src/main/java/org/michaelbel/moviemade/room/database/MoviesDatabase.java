package org.michaelbel.moviemade.room.database;

import org.michaelbel.moviemade.room.dao.MovieDao;
import org.michaelbel.moviemade.room.entity.Movie;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}