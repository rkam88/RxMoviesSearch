package net.rusnet.rxmoviessearch.search.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.domain.usecase.RemoteDataSourceUseCase;
import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.SearchResult;

import io.reactivex.Single;

public class PerformSearch extends RemoteDataSourceUseCase<String, Single<SearchResult>> {

    public PerformSearch(@NonNull IMoviesRemoteDataSource moviesRemoteDataSource) {
        super(moviesRemoteDataSource);
    }

    @NonNull
    @Override
    public Single<SearchResult> buildUseCaseObservable(@Nullable String requestValues) {
        if (requestValues == null) throw new IllegalArgumentException();
        return mMoviesRemoteDataSource.performSearch(requestValues);
    }

}
