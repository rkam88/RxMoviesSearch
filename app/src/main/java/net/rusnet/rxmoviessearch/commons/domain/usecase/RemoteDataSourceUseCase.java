package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.data.source.IMoviesRemoteDataSource;

import io.reactivex.Scheduler;

public abstract class RemoteDataSourceUseCase<R, T> extends UseCase<R, T> {

    protected IMoviesRemoteDataSource mMoviesRemoteDataSource;

    public RemoteDataSourceUseCase(
            @NonNull Scheduler mainThreadScheduler,
            @NonNull Scheduler workerThreadScheduler,
            @NonNull IMoviesRemoteDataSource moviesLocalDataSource) {
        super(mainThreadScheduler, workerThreadScheduler);
        mMoviesRemoteDataSource = moviesLocalDataSource;
    }

}
