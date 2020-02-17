package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.functions.Function;

public class MoviesLocalDataSource implements IMoviesLocalDataSource {

    private static final String EMPTY_STRING = "";

    private MovieDao mMovieDao;

    public MoviesLocalDataSource(@NonNull MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    @NonNull
    @Override
    public Maybe<List<Movie>> getAllMovies() {
        return mMovieDao.getAllMovies()
                .map(new Function<RoomMovie[], List<Movie>>() {
                    @Override
                    public List<Movie> apply(RoomMovie[] roomMovies) throws Exception {
                        List<Movie> movieList = new ArrayList<>();
                        for (RoomMovie roomMovie : roomMovies) {
                            movieList.add(new Movie(roomMovie.getTitle(),
                                    roomMovie.getYear(),
                                    roomMovie.getPosterURL(),
                                    roomMovie.getImdbID(),
                                    true));
                        }
                        return movieList;
                    }
                });
    }

    @NonNull
    @Override
    public Completable addMovie(@NonNull Movie movie) {
        return mMovieDao.addMovie(new RoomMovie(movie.getTitle(),
                movie.getYear(),
                movie.getImdbID(),
                movie.getPosterURL()));
    }

    @NonNull
    @Override
    public Completable deleteMovie(@NonNull String imdbId) {
        return mMovieDao.deleteMovie(imdbId);
    }

}
