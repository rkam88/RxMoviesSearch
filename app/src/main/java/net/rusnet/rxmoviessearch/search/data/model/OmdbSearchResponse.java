package net.rusnet.rxmoviessearch.search.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OmdbSearchResponse {

    @SerializedName("Search")
    @Expose
    private List<OmdbMovie> mOmdbMovies = null;
    @SerializedName("totalResults")
    @Expose
    private String mTotalResults;
    @SerializedName("Response")
    @Expose
    private String mResponse;

    public List<OmdbMovie> getOmdbMovies() {
        return mOmdbMovies;
    }

    public void setOmdbMovies(List<OmdbMovie> omdbMovies) {
        mOmdbMovies = omdbMovies;
    }

    public String getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(String totalResults) {
        mTotalResults = totalResults;
    }

    public String getResponse() {
        return mResponse;
    }

    public void setResponse(String response) {
        mResponse = response;
    }

}
