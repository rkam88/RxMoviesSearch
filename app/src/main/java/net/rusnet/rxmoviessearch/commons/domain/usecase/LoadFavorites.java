package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;

@Singleton
public class LoadFavorites extends DBUseCase<Void, Maybe<List<Movie>>> {

    @Inject
    public LoadFavorites(@NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(moviesLocalDataSource);
    }

    @NonNull
    @Override
    public Maybe<List<Movie>> buildUseCaseObservable(@Nullable Void aVoid) {
        return mMoviesLocalDataSource.getAllMovies();
    }

}
