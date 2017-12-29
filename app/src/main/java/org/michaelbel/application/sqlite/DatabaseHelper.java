package org.michaelbel.application.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.michaelbel.application.moviemade.annotation.Beta;
import org.michaelbel.application.rest.model.Movie;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "MOVIES_DATABASE";

    private static final String MOVIES_TABLE = "MOVIES";
    private static final String KEY_MOVIE_ID = "_id";
    private static final String KEY_MOVIE_TMDB_ID = "tmdb_id";
    private static final String KEY_MOVIE_TITLE = "movie_title";
    private static final String KEY_MOVIE_ORIGINAL_TITLE = "movie_original_title";
    private static final String KEY_MOVIE_OVERVIEW = "movie_overview";
    private static final String KEY_MOVIE_RUNTIME = "movie_runtime";
    private static final String KEY_MOVIE_RELEASE_DATE = "movie_release_date";
    private static final String KEY_MOVIE_ISFAVORITE = "movie_favorite";
    private static final String KEY_MOVIE_BACKDROP_PATH = "movie_backdrop_path";
    private static final String KEY_MOVIE_POSTER_PATH = "movie_poster_path";

    private static volatile DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + MOVIES_TABLE + " ("
                + KEY_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_MOVIE_TMDB_ID + " INTEGER, "
                + KEY_MOVIE_TITLE + " TEXT, "
                + KEY_MOVIE_ORIGINAL_TITLE + " TEXT, "
                + KEY_MOVIE_OVERVIEW + " TEXT, "
                + KEY_MOVIE_RUNTIME + " INTEGER, "
                + KEY_MOVIE_RELEASE_DATE + " TEXT, "
                + KEY_MOVIE_ISFAVORITE + " INTEGER, "
                + KEY_MOVIE_BACKDROP_PATH + " INTEGER, "
                + KEY_MOVIE_POSTER_PATH + " INTEGER) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIES_TABLE);
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_TMDB_ID, movie.id);
        values.put(KEY_MOVIE_TITLE, movie.title);
        values.put(KEY_MOVIE_ORIGINAL_TITLE, movie.originalTitle);
        values.put(KEY_MOVIE_OVERVIEW, movie.overview);
        values.put(KEY_MOVIE_RUNTIME, movie.runtime);
        values.put(KEY_MOVIE_RELEASE_DATE, movie.releaseDate);
        values.put(KEY_MOVIE_BACKDROP_PATH, movie.backdropPath);
        values.put(KEY_MOVIE_POSTER_PATH, movie.posterPath);

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(MOVIES_TABLE, null, values);
        database.close();
    }

    public void addMovie(int movieId) {
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_TMDB_ID, movieId);

        SQLiteDatabase database = this.getWritableDatabase();
        database.insert(MOVIES_TABLE, null, values);
        database.close();
    }

    public void removeMovie(int movieId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(MOVIES_TABLE, KEY_MOVIE_TMDB_ID + " = ?", new String[]{String.valueOf(movieId)});
        database.close();
    }

    public void updateMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_TITLE, movie.title);
        values.put(KEY_MOVIE_ORIGINAL_TITLE, movie.originalTitle);
        values.put(KEY_MOVIE_OVERVIEW, movie.overview);
        values.put(KEY_MOVIE_RUNTIME, movie.runtime);
        values.put(KEY_MOVIE_RELEASE_DATE, movie.releaseDate);

        SQLiteDatabase database = this.getWritableDatabase();
        database.update(MOVIES_TABLE, values, KEY_MOVIE_TMDB_ID + " = ? ", new String[]{String.valueOf(movie.id)});
        database.close();
    }

    public boolean isMovieExist(int tmdbId) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MOVIES_TABLE + " WHERE " + KEY_MOVIE_TMDB_ID + " = ?", new String[]{String.valueOf(tmdbId)});
        boolean isExist = cursor.getCount() > 0;
        cursor.close();
        return isExist;
    }

    public boolean isMovieFavorite(int movid_id) {
        return false;
    }

    public long getMoviesCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(database, MOVIES_TABLE);
        database.close();
        return count;
    }

    public Movie getMovie(int tmdbId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + MOVIES_TABLE + " WHERE " + KEY_MOVIE_TMDB_ID + " = ?", new String[]{String.valueOf(tmdbId)});
        cursor.moveToFirst();

        Movie movie = new Movie();
        movie.title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_TITLE));
        movie.originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_ORIGINAL_TITLE));
        movie.overview = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_OVERVIEW));
        movie.runtime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVIE_RUNTIME));
        movie.releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_RELEASE_DATE));

        cursor.close();
        database.close();
        return movie;
    }

    public List<Movie> getMoviesList() {
        List<Movie> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        try (Cursor cursor = database.rawQuery("SELECT * FROM " + MOVIES_TABLE, null)) {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie();
                    movie.id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVIE_TMDB_ID));
                    movie.title = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_TITLE));
                    movie.originalTitle = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_ORIGINAL_TITLE));
                    movie.overview = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_OVERVIEW));
                    movie.runtime = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_MOVIE_RUNTIME));
                    movie.releaseDate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_RELEASE_DATE));
                    movie.backdropPath = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_BACKDROP_PATH));
                    movie.posterPath = cursor.getString(cursor.getColumnIndexOrThrow(KEY_MOVIE_POSTER_PATH));

                    list.add(movie);
                } while (cursor.moveToNext());
            }
        }

        database.close();
        return list;
    }
}