package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class LoadFavorites extends DBUseCase<Void, List<Movie>> {

    public LoadFavorites(@NonNull Scheduler mainThreadScheduler,
                         @NonNull Scheduler workerThreadScheduler,
                         @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(mainThreadScheduler, workerThreadScheduler, moviesLocalDataSource);
    }

    @NonNull
    @Override
    protected Observable<List<Movie>> buildUseCaseObservable(@Nullable Void requestValues) {
        return mMoviesLocalDataSource.getAllMovies();
    }

}
