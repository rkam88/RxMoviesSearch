package net.rusnet.rxmoviessearch.search.domain.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SearchResult {

    @NonNull
    private final SearchResultStatus mSearchResultStatus;
    private final long mTotalResults;
    @Nullable
    private final List<Movie> mMovieList;

    public SearchResult(@NonNull SearchResultStatus searchResultStatus,
                        long totalResults,
                        @Nullable List<Movie> movieList) {
        if (searchResultStatus == SearchResultStatus.SUCCESSFUL && movieList == null)
            throw new IllegalArgumentException();
        mSearchResultStatus = searchResultStatus;
        mTotalResults = totalResults;
        mMovieList = movieList;
    }

    @NonNull
    public SearchResultStatus getSearchResultStatus() {
        return mSearchResultStatus;
    }

    public long getTotalResults() {
        return mTotalResults;
    }

    @Nullable
    public List<Movie> getMovieList() {
        return mMovieList;
    }
}
