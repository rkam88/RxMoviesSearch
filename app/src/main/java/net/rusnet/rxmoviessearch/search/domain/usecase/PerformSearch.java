package net.rusnet.rxmoviessearch.search.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.domain.usecase.UseCase;
import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

public class PerformSearch extends UseCase<String, SearchResult> {

    private IMoviesRemoteDataSource mMoviesRemoteDataSource;

    public PerformSearch(@NonNull IMoviesRemoteDataSource moviesRemoteDataSource) {
        mMoviesRemoteDataSource = moviesRemoteDataSource;
    }

    @Override
    public void execute(@Nullable String requestValues, @NonNull final Callback<SearchResult> callback) {
        if (requestValues == null) throw new IllegalArgumentException();
        mMoviesRemoteDataSource.performSearch(requestValues, new IMoviesRemoteDataSource.onSearchResultCallback() {
            @Override
            public void onResult(@NonNull SearchResult searchResult) {
                callback.onResult(searchResult);
            }
        });
    }

}
