package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;

import io.reactivex.Scheduler;

public abstract class DBUseCase<R, T> extends UseCase<R, T> {

    protected IMoviesLocalDataSource mMoviesLocalDataSource;

    public DBUseCase(@NonNull Scheduler mainThreadScheduler,
                     @NonNull Scheduler workerThreadScheduler,
                     @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(mainThreadScheduler, workerThreadScheduler);
        mMoviesLocalDataSource = moviesLocalDataSource;
    }

}
