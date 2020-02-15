package net.rusnet.rxmoviessearch.search.domain.model;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchResult {

    private final long mTotalResults;
    @NonNull
    private final List<Movie> mMovieList;

    public SearchResult(long totalResults,
                        @NonNull List<Movie> movieList) {
        mTotalResults = totalResults;
        mMovieList = movieList;
    }

    public long getTotalResults() {
        return mTotalResults;
    }

    @NonNull
    public List<Movie> getMovieList() {
        return mMovieList;
    }
}
