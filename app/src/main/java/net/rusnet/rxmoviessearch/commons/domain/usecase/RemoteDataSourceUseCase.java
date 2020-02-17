package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;

public abstract class RemoteDataSourceUseCase<V, T> implements UseCase<V, T> {

    protected IMoviesRemoteDataSource mMoviesRemoteDataSource;

    public RemoteDataSourceUseCase(
            @NonNull IMoviesRemoteDataSource moviesRemoteDataSource) {
        mMoviesRemoteDataSource = moviesRemoteDataSource;
    }

}
