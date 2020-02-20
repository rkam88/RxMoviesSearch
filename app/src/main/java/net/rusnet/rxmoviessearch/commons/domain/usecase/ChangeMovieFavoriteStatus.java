package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class ChangeMovieFavoriteStatus extends DBUseCase<Movie, Completable> {

    @Inject
    public ChangeMovieFavoriteStatus(
            @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(moviesLocalDataSource);
    }

    @NonNull
    @Override
    public Completable buildUseCaseObservable(@Nullable Movie requestValues) {
        if (requestValues == null) throw new IllegalArgumentException();
        if (requestValues.isInFavorites()) {
            return mMoviesLocalDataSource.addMovie(requestValues);
        } else {
            return mMoviesLocalDataSource.deleteMovie(requestValues.getImdbID());
        }
    }

}
