package net.rusnet.rxmoviessearch.search.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OMDbSearchResponse {

    @SerializedName("Search")
    @Expose
    private List<OMDbMovie> mOMDbMovies = null;
    @SerializedName("totalResults")
    @Expose
    private String mTotalResults;
    @SerializedName("Response")
    @Expose
    private String mResponse;

    public List<OMDbMovie> getOMDbMovies() {
        return mOMDbMovies;
    }

    public void setOMDbMovies(List<OMDbMovie> OMDbMovies) {
        mOMDbMovies = OMDbMovies;
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
