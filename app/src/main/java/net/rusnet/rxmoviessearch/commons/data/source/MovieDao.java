package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;

import io.reactivex.Completable;
import io.reactivex.Maybe;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie_table")
    Maybe<RoomMovie[]> getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMovie(@NonNull RoomMovie movieToAdd);

    @Query("DELETE FROM movie_table WHERE imdb_id = :imdbId")
    Completable deleteMovie(@NonNull String imdbId);

}
