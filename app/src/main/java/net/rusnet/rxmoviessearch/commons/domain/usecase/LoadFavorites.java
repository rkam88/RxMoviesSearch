package net.rusnet.rxmoviessearch.commons.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.rusnet.rxmoviessearch.commons.data.source.IMoviesLocalDataSource;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

public class LoadFavorites extends DBUseCase<Void, List<Movie>> {

    public LoadFavorites(@NonNull AsyncUseCaseExecutor useCaseExecutor, @NonNull IMoviesLocalDataSource moviesLocalDataSource) {
        super(useCaseExecutor, moviesLocalDataSource);
    }

    @NonNull
    @Override
    protected List<Movie> doInBackground(@Nullable Void requestValues) {
        return mMoviesLocalDataSource.getAllMovies();
    }
}
