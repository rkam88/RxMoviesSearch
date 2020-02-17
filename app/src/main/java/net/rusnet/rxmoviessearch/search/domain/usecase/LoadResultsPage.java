package net.rusnet.rxmoviessearch.search.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.domain.usecase.RemoteDataSourceUseCase;
import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import io.reactivex.Single;

public class LoadResultsPage
        extends RemoteDataSourceUseCase<LoadResultsPageRequestValues, Single<SearchResult>> {

    public LoadResultsPage(@NonNull IMoviesRemoteDataSource moviesRemoteDataSource) {
        super(moviesRemoteDataSource);
    }

    @NonNull
    @Override
    public Single<SearchResult> buildUseCaseObservable(@Nullable LoadResultsPageRequestValues requestValues) {
        if (requestValues == null) throw new IllegalArgumentException();
        return mMoviesRemoteDataSource.getPage(
                requestValues.getSearchQuery(),
                requestValues.getPageToLoad());
    }

}
