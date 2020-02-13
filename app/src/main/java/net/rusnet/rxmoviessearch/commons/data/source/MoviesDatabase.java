package net.rusnet.rxmoviessearch.commons.data.source;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;

@Database(entities = {RoomMovie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static MoviesDatabase INSTANCE;

    private static final String NAME = "movies.db";
    private static final Object LOCK = new Object();

    public static MoviesDatabase getDatabase(Context context) {
        synchronized (LOCK) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        MoviesDatabase.class,
                        MoviesDatabase.NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }
}
