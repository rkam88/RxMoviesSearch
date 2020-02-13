package net.rusnet.rxmoviessearch.search.domain.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Movie implements Serializable {

    private final String mTitle;
    private final String mYear;
    private final String mPosterURL;
    private final String mImdbID;
    private boolean mIsInFavorites;

    public Movie(@NonNull String title,
                 @NonNull String year,
                 @NonNull String posterURL,
                 @NonNull String imdbID,
                 boolean isInFavorites) {
        mTitle = title;
        mYear = year;
        mPosterURL = posterURL;
        mImdbID = imdbID;
        mIsInFavorites = isInFavorites;
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
    public String getPosterURL() {
        return mPosterURL;
    }

    @NonNull
    public String getImdbID() {
        return mImdbID;
    }

    public boolean isInFavorites() {
        return mIsInFavorites;
    }

    public void setInFavorites(boolean inFavorites) {
        mIsInFavorites = inFavorites;
    }

}
