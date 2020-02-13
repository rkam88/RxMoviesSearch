package net.rusnet.rxmoviessearch.commons.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie_table")
public class RoomMovie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private int mMovieId;

    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;

    @NonNull
    @ColumnInfo(name = "year")
    private final String mYear;

    @NonNull
    @ColumnInfo(name = "imdb_id")
    private final String mImdbID;

    @NonNull
    @ColumnInfo(name = "poster_url")
    private final String mPosterURL;

    public RoomMovie(int movieId, @NonNull String title, @NonNull String year, @NonNull String imdbID, @NonNull String posterURL) {
        mMovieId = movieId;
        mTitle = title;
        mYear = year;
        mImdbID = imdbID;
        mPosterURL = posterURL;
    }

    @Ignore
    public RoomMovie(@NonNull String title, @NonNull String year, @NonNull String imdbID, @NonNull String posterURL) {
        mTitle = title;
        mYear = year;
        mImdbID = imdbID;
        mPosterURL = posterURL;
    }

    public int getMovieId() {
        return mMovieId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getYear() {
        return mYear;
    }

    @NonNull
    public String getImdbID() {
        return mImdbID;
    }

    @NonNull
    public String getPosterURL() {
        return mPosterURL;
    }
}
