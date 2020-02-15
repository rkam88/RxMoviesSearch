package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class ChangeMovieFavoriteStatus extends DBUseCase<Movie, Void> {

    public ChangeMovieFavoriteStatus(@NonNull Scheduler mainThreadScheduler, @NonNull Scheduler workerThreadScheduler, @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(mainThreadScheduler, workerThreadScheduler, moviesLocalDataSource);
    }

    @NonNull
    @Override
    protected Observable<Void> buildUseCaseObservable(@Nullable Movie requestValues) {
        if (requestValues == null) throw new IllegalArgumentException();
        if (requestValues.isInFavorites()) {
            return mMoviesLocalDataSource.addMovie(requestValues).toObservable();
        } else {
            return mMoviesLocalDataSource.deleteMovie(requestValues.getImdbID()).toObservable();
        }
    }

}
