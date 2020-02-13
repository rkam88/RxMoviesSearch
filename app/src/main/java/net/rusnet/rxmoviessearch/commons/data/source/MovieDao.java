package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie_table")
    RoomMovie[] getAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovie(RoomMovie movieToAdd);

    @Query("DELETE FROM movie_table WHERE imdb_id = :imdbId")
    void deleteMovie(@NonNull String imdbId);

}
