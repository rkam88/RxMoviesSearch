package net.rusnet.rxmoviessearch.search.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.domain.usecase.UseCase;
import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

public class LoadResultsPage extends UseCase<LoadResultsPageRequestValues, SearchResult> {

    private IMoviesRemoteDataSource mMoviesRemoteDataSource;

    public LoadResultsPage(@NonNull IMoviesRemoteDataSource moviesRemoteDataSource) {
        mMoviesRemoteDataSource = moviesRemoteDataSource;
    }

    @Override
    public void execute(@Nullable LoadResultsPageRequestValues requestValues, @NonNull final Callback<SearchResult> callback) {
        if (requestValues == null) throw new IllegalArgumentException();
        mMoviesRemoteDataSource.getPage(requestValues.getSearchQuery(),
                requestValues.getPageToLoad(),
                new IMoviesRemoteDataSource.onSearchResultCallback() {
                    @Override
                    public void onResult(@NonNull SearchResult searchResult) {
                        callback.onResult(searchResult);
                    }
                });
    }

}
