package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.List;

public interface IMoviesLocalDataSource {

    @NonNull
    List<Movie> getAllMovies();

    void addMovie(@NonNull Movie movie);

    void deleteMovie(@NonNull String imdbId);

}
