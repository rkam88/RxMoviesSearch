package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface IMoviesLocalDataSource {

    @NonNull
    Maybe<List<Movie>> getAllMovies();

    @NonNull
    Completable addMovie(@NonNull Movie movie);

    @NonNull
    Completable deleteMovie(@NonNull String imdbId);

}
