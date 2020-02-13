package net.rusnet.rxmoviessearch.search.domain.usecase;

public class LoadResultsPageRequestValues {
    private final int mPageToLoad;
    private final String mSearchQuery;

    public LoadResultsPageRequestValues(int pageToLoad, String searchQuery) {
        mPageToLoad = pageToLoad;
        mSearchQuery = searchQuery;
    }

    public int getPageToLoad() {
        return mPageToLoad;
    }

    public String getSearchQuery() {
        return mSearchQuery;
    }
}