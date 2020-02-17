package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;

public abstract class DBUseCase<V, T> implements UseCase<V, T> {

    protected IMoviesLocalDataSource mMoviesLocalDataSource;

    public DBUseCase(
            @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        mMoviesLocalDataSource = moviesLocalDataSource;
    }

}
