package net.rusnet.rxmoviessearch.search.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OmdbMovie {

    @SerializedName("Title")
    @Expose
    private String mTitle;
    @SerializedName("Year")
    @Expose
    private String mYear;
    @SerializedName("imdbID")
    @Expose
    private String mImdbID;
    @SerializedName("Type")
    @Expose
    private String mType;
    @SerializedName("Poster")
    @Expose
    private String mPoster;
    private final static long serialVersionUID = -5622617283526544045L;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

    public String getImdbID() {
        return mImdbID;
    }

    public void setImdbID(String imdbID) {
        mImdbID = imdbID;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String poster) {
        mPoster = poster;
    }
}
