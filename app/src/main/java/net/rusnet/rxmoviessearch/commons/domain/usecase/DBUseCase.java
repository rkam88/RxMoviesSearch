package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;

public abstract class DBUseCase<Q, R> extends AsyncUseCase<Q, R> {

    protected IMoviesLocalDataSource mMoviesLocalDataSource;

    public DBUseCase(@NonNull AsyncUseCaseExecutor useCaseExecutor,
                     @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(useCaseExecutor);
        mMoviesLocalDataSource = moviesLocalDataSource;
    }

}
