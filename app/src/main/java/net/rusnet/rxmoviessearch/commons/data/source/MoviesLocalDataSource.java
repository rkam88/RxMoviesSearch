package net.rusnet.rxmoviessearch.commons.data.source;

import androidx.annotation.NonNull;

import net.rusnet.rxmoviessearch.commons.data.model.RoomMovie;
import net.rusnet.rxmoviessearch.search.domain.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.functions.Function;

public class MoviesLocalDataSource implements IMoviesLocalDataSource {

    private static final String EMPTY_STRING = "";

    private MovieDao mMovieDao;

    public MoviesLocalDataSource(@NonNull MovieDao movieDao) {
        mMovieDao = movieDao;
    }

    @NonNull
    @Override
    public Single<List<Movie>> getAllMovies() {
        return Single.create(new SingleOnSubscribe<List<RoomMovie>>() {
            @Override
            public void subscribe(SingleEmitter<List<RoomMovie>> emitter) throws Exception {
                emitter.onSuccess(Arrays.asList(mMovieDao.getAllMovies()));
            }
        })
                .map(new Function<List<RoomMovie>, List<Movie>>() {
            @Override
            public List<Movie> apply(List<RoomMovie> roomMovies) throws Exception {
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
